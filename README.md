# Bunnyhop (Forge 1.20.1)

Базовый каркас Minecraft-мода под **IntelliJ IDEA** для **Forge 1.20.1**.

Теперь в моде есть:
- более плавный стрейф в воздухе (инпут сглаживается, нет резкого мгновенного рывка влево/вправо)
- Source-like ускорение в воздухе и на земле
- сохранение импульса между прыжками
- прыжок колесом мыши вниз (scroll down jump)

## IntelliJ IDEA

1. Открой папку проекта (`File -> Open`).
2. Дождись импорта Gradle.
3. Укажи JDK **17** (для Forge 1.20.1).
4. Запусти `genIntellijRuns`, затем `runClient`.

## Где логика движения

- Мод: `dev.bunnyhop.BunnyhopMod`
- Константы физики: `dev.bunnyhop.movement.BunnyhopMovementConfig`
- Контроллер ускорения/air-control: `dev.bunnyhop.movement.BunnyhopMovementController`
- Серверный тик: `dev.bunnyhop.forge.BunnyhopMovementEvents`
- Клиентский инпут + колесо мыши: `dev.bunnyhop.forge.client.BunnyhopClientEvents`
- Клиентские настройки: `dev.bunnyhop.config.BunnyhopClientConfig`

## Настройки мода

Файл создается Forge-ом: `config/bunnyhop-client.toml`

Ключевые параметры:
- `enableBunnyhop = true` — вкл/выкл модифицированное движение
- `scrollDownJump = true` — прыжок на колесико мыши вниз
- `strafeSmoothing = 0.18` — плавность стрейфа (чем выше, тем плавнее и медленнее реакция)

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
