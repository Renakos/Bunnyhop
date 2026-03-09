# Bunnyhop (Forge 1.20.1)

Базовый каркас Minecraft-мода под **IntelliJ IDEA** для **Forge 1.20.1**.

Теперь в моде есть:
- плавный стрейф в воздухе (без резкого мгновенного рывка строго влево/вправо)
- Source-like ускорение в воздухе и на земле
- сохранение части forward momentum при стрейфах
- отключение bunnyhop-физики в воде/лаве/полете
- прыжок колесом мыши вниз (scroll down jump)
- богатый экран настроек мода в **Mods -> Bunnyhop -> Config** (переключатели + кнопки +/- для всех параметров физики)

## IntelliJ IDEA

1. Открой папку проекта (`File -> Open`).
2. Дождись импорта Gradle.
3. Укажи JDK **17** (для Forge 1.20.1).
4. Запусти `genIntellijRuns`, затем `runClient`.

## Где логика движения

- Мод: `dev.bunnyhop.BunnyhopMod`
- Контроллер ускорения/air-control: `dev.bunnyhop.movement.BunnyhopMovementController`
- Серверный тик: `dev.bunnyhop.forge.BunnyhopMovementEvents`
- Клиентский инпут + колесо мыши: `dev.bunnyhop.forge.client.BunnyhopClientEvents`
- Экран настроек: `dev.bunnyhop.forge.client.BunnyhopSettingsScreen`
- Клиентские настройки: `dev.bunnyhop.config.BunnyhopClientConfig`

## Настройки мода

Файл создается Forge-ом: `config/bunnyhop-client.toml`

Ключевые параметры:
- `enableBunnyhop`
- `scrollDownJump`
- `strafeSmoothing`
- `groundAcceleration`
- `airAcceleration`
- `airControl`
- `groundFriction`
- `momentumRetention`
- `maxAirSpeed`
- `maxGroundSpeed`

> Если кажется, что нет разницы с ваниллой: увеличь `airAcceleration` до `0.08-0.12`, `maxAirSpeed` до `1.0-1.3`, а `strafeSmoothing` держи в диапазоне `0.10-0.22`.

## Ошибка «проект одновременно Forge и Fabric»

Если у тебя всплывает ошибка с `PlayerEntityMixin.java` и пакетами `net.minecraft.entity.*`, значит в ветке Forge остались файлы от Fabric после конфликтного merge.

### Как правильно сливать

1. Перед merge зафиксируй текущее состояние:
   - `git status`
2. Делай merge и **не жми вслепую "Accept Current" везде**.
3. Для Forge-ветки нужно оставить:
   - `src/main/resources/META-INF/mods.toml`
   - `src/main/resources/pack.mcmeta`
   - `src/main/java/dev/bunnyhop/forge/...`
4. Для Forge-ветки нужно удалить Fabric-остатки:
   - `src/main/resources/fabric.mod.json`
   - `src/main/resources/bunnyhop.mixins.json`
   - `src/main/java/dev/bunnyhop/mixin/PlayerEntityMixin.java`
5. После merge запусти:
   - `./gradlew clean compileJava`

В этом репозитории добавлена проверка `verifyNoFabricLeftovers`, которая остановит сборку, если Fabric-файлы случайно остались.
