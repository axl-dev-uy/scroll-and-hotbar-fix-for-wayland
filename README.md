# Scroll and Hotbar fix for Wayland
A Forge mod for Minecraft 1.12.2 that fixes duplicate scroll events on Wayland compositors.

# The Problem
On Linux with Wayland, scroll events in Minecraft fire twice on every other scroll, affecting both hotbar selection and GUI scrolling (JEI, inventory screens, etc.). This is caused by Wayland's high-resolution scrolling protocol sending multiple event types per scroll that older LWJGL versions don't handle correctly.

## The Fix
This mod intercepts scroll events and suppresses duplicates that arrive within a configurable time window.


## Configuration
A config file is generated at config/scroll-hotbar-fix.cfg on first launch.
OptionDefaultDescriptionscrollThreshold25Time in milliseconds to suppress duplicate scroll events. Increase if double scrolling persists, decrease if fast intentional scrolls are being skipped.


## Compatibility
-   Minecraft 1.12.2
-   Forge 14.23.5.2860+
-   Linux with Wayland (KDE Plasma tested)
-   Java 8 and 25

## Notes
This mod was made specifically with the [MeatballCraft](https://www.curseforge.com/minecraft/modpacks/meatballcraft) modpack in mind and has been tested there. It should work with any 1.12.2 Forge modpack on Wayland, but MeatballCraft is the primary use case.

## Disclaimer
This mod was written in about 30 minutes as a workaround for a very specific Linux/Wayland issue. It works, but I make no guarantees about its stability or compatibility with every possible setup. Use at your own risk. I am not responsible for any damages, corrupted worlds, existential crises, or scroll-related injuries that may result from its use.
