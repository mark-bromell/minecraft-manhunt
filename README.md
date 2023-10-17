# Minecraft Manhunt

This is a Spigot plugin for a Minecraft Manhunt gamemode. This is inspired by Dream's
Manhunt video series [(one of his videos)](https://www.youtube.com/watch?v=cIY95KCnnNk).
I thought it would be cool to develop a plugin of my own for Minecraft and have some fun
with friends playing a manhunt.

Currently it's a simple plugin with only the fundamental features, and it is not actively
maintained. If you wish to run this plugin on your Spigot server then please refer to
their documentation on how to develop plugins, you can then learn to build this plugin
and add it to your server. https://www.spigotmc.org/wiki/spigot-plugin-development/

If you do fork this and have any issues feel free to reach out to me and maybe I can
help you out.

## Commands

[`/hunter add [<player1> <player2>...]`](#hunter-add)

[`/hunter remove [<player1> <player2>...]`](#hunter-remove)

[`/hunter list`](#hunter-list)

[`/hunted <player>`](#hunted)

### hunter add
#### Usage
`/hunter add [<player1> <player2>...]`

#### Description
Add a player (or a collection of players) as a hunter. The players must be online in
order to add them as a hunter. When you add a player as a hunter, a compass will be dropped at
their location in the world which will be used to track the hunted.

#### Example
`/hunter add Notch`

`/hunter add mark_b Notch`

### hunter remove 
#### Usage
`/hunter remove [<player1> <player2>...]`

#### Description
Remove a player (or a collection of players) as a hunter. The players must be online in
order to remove them as a hunter. When you remove a player as a hunter, their compass will no
longer track the location of the hunted.

#### Example
`/hunter remove Notch`

`/hunter remove mark_b Notch`

### hunter list
#### Usage
`/hunter list`

#### Description
List all the hunters that are currently online.

### hunted
#### Usage
`/hunted <player>`

#### Description
Sets a player as the hunted. This will trigger the hunters' compasses to start tracking the
hunted player.

#### Example
`/hunted Notch`
