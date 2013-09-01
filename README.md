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

Known issue
=============
Scanner not closed.
If change to close after use - program behavior not user friendly while wait input.

