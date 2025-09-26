# RWelcomer

A Minecraft plugin that welcomes new players and rewards those who greet them.

## Features



- Automatic welcome messages for new players joining the server
- Reward system for players who greet newcomers with "hello <playername>"
- Configurable reward amounts and maximum number of rewarded players
- Vault integration for economy support
- In-game configuration commands with tab completion
- Customizable messages and settings

## Requirements

- Minecraft Server 1.21.1+
- Spigot/Paper
- Vault plugin
- Economy plugin (compatible with Vault)

## Installation

1. Download the plugin JAR file from \[releases](https://github.com/rogueliver/rwelcomer/releases) (or build it yourself)
2. Place the JAR file in your server's `plugins` folder
3. Ensure Vault and an economy plugin are installed
4. Restart your server
5. Configure the plugin using `/rwelcomer` commands or edit `config.yml`

## Commands

- `/rwelcomer` - Show available settings
- `/rwelcomer reload` - Reload configuration
- `/rwelcomer welcome-message <message>` - Set welcome message
- `/rwelcomer reward-amount <amount>` - Set reward amount
- `/rwelcomer max-rewarded-players <number>` - Set maximum rewarded players

Aliases: `/rw`



## Permissions

- `rwelcomer.admin` - Access to configuration commands (default: op)

## Configuration

The plugin creates a [`config.yml`](src/main/resources/config.yml) file with the following options:



```yaml
welcome:
  enabled: true
  message: "&a&lWelcome &f{player} &a&lto the server!"
  broadcast: true

greetings:
  enabled: true
  reward-amount: 5000
  max-rewarded-players: 3
  reward-message: "&a&l+${amount} &ffor welcoming &a{target}!"
  already-rewarded-message: "&cYou have already been rewarded for welcoming someone!"
  max-rewards-reached-message: "&cThe maximum number of welcome rewards has been reached!"

messages:
  config-reloaded: "&aConfiguration reloaded!"
  invalid-setting: "&cInvalid setting! Available: welcome-message, reward-amount, max-rewarded-players"
  setting-updated: "&aSetting &f{setting} &aupdated to &f{value}"
  no-permission: "&cYou don't have permission to use this command!"
```



## How It Works

1. When a new player joins the server, a welcome message is displayed
2. Players can greet newcomers by typing "hello <playername>" in chat
3. The first 3 unique players to greet someone receive a configurable reward
4. Players cannot reward themselves or receive multiple rewards
5. Reward tracking resets when the plugin is reloaded

## Building

Requirements:

- Java 17+

- Maven

```bash

mvn clean package

```



The compiled JAR will be in the `target` directory.



## License

This project is under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

Made by rogueliver (RL)

* GitHub: https://github.com/rogueliver
* Discord: https://discord.com/users/1354013258884972610
