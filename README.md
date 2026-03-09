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

## Дальше по плану

Для полной замены `PlayerEntity.move` и низкоуровневой коллизии добавляй Mixin/Coremod слой поверх этой базы.
