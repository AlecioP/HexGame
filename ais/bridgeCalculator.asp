% bridge(C1_X,C1_Y,C2_X,C2_Y)
% if controll(2) Ai is Blue and goes horizontally

adj(X1,Y1,X2,Y2):-cell(_,X1,Y1),cell(_,X2,Y2),X2>X1-2,X2<X1+2,Y2>Y1-2,Y2<Y1+2.
:- adj(X1,Y1,X2,Y2),X2=X1+1,Y2=Y1-1.
:- adj(X1,Y1,X2,Y2),X2=X1-1,Y2=Y1+1.

_2bridge(X1,Y1,X2,Y2):- 2 = #count{X,Y: adj(X1,Y1,X,Y), adj(X2,Y2,X,Y), X!=X1, X!=X2, Y!=Y1, Y!=Y2} ,cell(Color,X1,Y1),cell(Color,X2,Y2), control(Color).
