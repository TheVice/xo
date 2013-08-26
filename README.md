xo
=========================

Simple game known as 'tic-tac-toe' written during learning Java.
Only human vs. human mode and only on local PC, no network support yet.

Console making and running
==========================
mkdir out
javac -sourcepath ./src ./src/Main.java -d ./out
cd out
java Main 

Simple running output
==========================
java Main 
Welcome in Xs and Os (a.k.a. Tic-tac-toe)!
Field size: 3 x 3
Field type: Bottom Left
Start position marked as S
From this point (that is 1 1) to width and height (that is 3 3)
The complex field is
---------
[ ][ ][ ]
[ ][ ][ ]
[S][ ][ ]
---------
Player of X please make your move (position x, y):1
1

---------
[ ][ ][ ]
[ ][ ][ ]
[X][ ][ ]
---------
If you want you can undo your last step (press 1 - to undo and not 1 to no).
0
Player of O please make your move (position x, y):2
2

---------
[ ][ ][ ]
[ ][O][ ]
[X][ ][ ]
---------
If you want you can undo your last step (press 1 - to undo and not 1 to no).
0
Player of X please make your move (position x, y):1
2

---------
[ ][ ][ ]
[X][O][ ]
[X][ ][ ]
---------
If you want you can undo your last step (press 1 - to undo and not 1 to no).
0
Player of O please make your move (position x, y):3
3

---------
[ ][ ][O]
[X][O][ ]
[X][ ][ ]
---------
If you want you can undo your last step (press 1 - to undo and not 1 to no).
0
Player of X please make your move (position x, y):1
3

---------
[X][ ][O]
[X][O][ ]
[X][ ][ ]
---------
The game is over
The winner is player X
Step - 1. Figure X on x = 1 y = 1
Step - 2. Figure O on x = 2 y = 2
Step - 3. Figure X on x = 1 y = 2
Step - 4. Figure O on x = 3 y = 3
Step - 5. Figure X on x = 1 y = 3


