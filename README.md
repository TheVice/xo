xo
=========================

Simple game known as 'tic-tac-toe' written during learning Java.
For human vs. human mode see tag or branch named like this mode,
also you can view one of previouse commit where this file do not changed.
Human vs. Human (hot seat), Ai vs. Ai and mix modes.
No network support yet.

Console making and running
==========================
mkdir out
javac -sourcepath ./src ./src/Main.java -d ./out
cd out
     java Main 
or (for making jar and run it)
     jar cfev xo.jar Main .
     java -jar xo.jar

Known issue
=============
Players in custom game mode can have the same figure,
if user type like that: already set figure do not checked.
Referee class nead refactoring.
At Input test use depracated class.

=============

   Copyright 2013 https://github.com/TheVice/

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

