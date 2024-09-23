## Subsystems

Subsystems are the major parts of the bot that can be though of independently.  For instance, the chassis and drive could be coded separately from an arm or a sensor.

The reason to have a subsystem class for the major parts of the bot is so that we can create an interface with the hardware and expose it in an easily consumable way that can be used in multiple opmodes or other code.