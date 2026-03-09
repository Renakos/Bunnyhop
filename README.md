# Bunnyhop (Fabric)

Базовый каркас Minecraft-мода под **IntelliJ IDEA** с полной заготовкой для переработки движения в стиле Source:

- Jump control
- Strafing boost
- Momentum retention
- Air acceleration
- Поведение `Shift` в прыжке без искусственного замедления
- Точки расширения для замены `PlayerEntity.move` и обработки коллизий

## Как открыть в IntelliJ IDEA

1. `File -> Open` и выбрать папку проекта.
2. IntelliJ распознает Gradle-проект автоматически.
3. Установить JDK 21 для проекта (Project SDK).
4. После синхронизации можно запускать задачи Gradle (`runClient`, `build` и т.д.).

## Важные точки кода

- Основной мод: `dev.bunnyhop.BunnyhopMod`
- Конфиг физики: `dev.bunnyhop.movement.BunnyhopMovementConfig`
- Контроллер движения: `dev.bunnyhop.movement.BunnyhopMovementController`
- Mixin в игрока: `dev.bunnyhop.mixin.PlayerEntityMixin`

## Примечание

В текущем окружении доступ к Fabric Maven может быть ограничен, поэтому синхронизация зависимостей может требовать рабочей сети/VPN.
