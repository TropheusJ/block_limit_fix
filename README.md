# Block Limit Fix
This mod fixes a [vanilla bug](https://bugs.mojang.com/browse/MC-258163) that 
occurs when there's roughly 500k unique Blockstates. When this bug is triggered,
a range of issues can occur from ghost blocks to desyncs.

Specifically, beyond 524288 states, the math for serializing the states to packets
breaks down due to a misplaced cast. This bug is fixed in 1.19.4 / 1.20, whichever
comes first. This mod backports it to older versions.

Thanks to [Patbox](https://github.com/Patbox) for debugging this and figuring 
out the solution.
