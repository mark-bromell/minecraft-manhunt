# Commands

[`/hunter add [<player1> <player2>...]`](#hunter-add)

[`/hunter remove [<player1> <player2>...]`](#hunter-remove)

[`/hunter list`](#hunter-list)

[`/hunted <player>`](#hunted)

## hunter add
#### Usage
`/hunter add [<player1> <player2>...]`

#### Description
Add a player (or a collection of players) as a hunter. The players must be online in
order to add them as a hunter. When you add a player as a hunter, a compass will be dropped at
their location in the world which will be used to track the hunted.

#### Example
`/hunter add Notch`

`/hunter add mark_b Notch`

## hunter remove 
#### Usage
`/hunter remove [<player1> <player2>...]`

#### Description
Remove a player (or a collection of players) as a hunter. The players must be online in
order to remove them as a hunter. When you remove a player as a hunter, their compass will no
longer track the location of the hunted.

#### Example
`/hunter remove Notch`

`/hunter remove mark_b Notch`

## hunter list
#### Usage
`/hunter list`

#### Description
List all the hunters that are currently online.

## hunted
#### Usage
`/hunted <player>`

#### Description
Sets a player as the hunted. This will trigger the hunters' compasses to start tracking the
hunted player.

#### Example
`/hunted Notch`