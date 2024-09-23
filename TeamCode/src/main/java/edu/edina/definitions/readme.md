## Definitions

One important programmatic concept is to favor using readable and understandable variable names over "magic numbers" or "magic strings".  Both of those are hardcoded values which are (hopefully) known and understood at the time they were added to the codebase, but are not necessarily clear over time as context is lost, or not clear to others *why* they are necessary.

Instead, we can create some classes representing things that we are going to be dealing with and put the numbers and strings that should be used consistently throughout the application.  For instance, if a servo should have a maximum rotation, we should manage that in one place instead of every opmode that it may be referenced in.

The definitions will work best if there are classes of logically grouped values.  For instance, having the names of the motors in the bot could be useful and would be a logical group.  If x/y coordinates for parts of the arena are needed, those could be an example of something else that could be logically grouped into a different file.