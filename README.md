# Bunnyhop (Forge 1.20.1)

Базовый каркас Minecraft-мода под **IntelliJ IDEA** для **Forge 1.20.1**.

Реализованы стартовые Source-style механики:
- Jump control
- Strafing boost
- Momentum retention
- Air acceleration
- Поведение `Shift` в воздухе без дополнительного замедления

## IntelliJ IDEA

1. Открой папку проекта (`File -> Open`).
2. Дождись импорта Gradle.
3. Укажи JDK **17** (для Forge 1.20.1).
4. Запусти `genIntellijRuns`, затем `runClient`.

## Где логика движения

- Мод: `dev.bunnyhop.BunnyhopMod`
- Константы физики: `dev.bunnyhop.movement.BunnyhopMovementConfig`
- Контроллер ускорения: `dev.bunnyhop.movement.BunnyhopMovementController`
- Подписка на тики игрока: `dev.bunnyhop.forge.BunnyhopMovementEvents`

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

## Дальше по плану

Для полной замены `PlayerEntity.move` и низкоуровневой коллизии добавляй Mixin/Coremod слой поверх этой базы.
