% To include to know the color of ai

%AI HAS TO COVER ONLY ONE ROLE
:- ai(X),ai(Y),X != Y.
%THERE ARE ONLY TWO ROLES
:- ai(X),X>2.
:- ai(X),X<1.

%IF NOT SPECIFIED THEN AI TAKES THE ROLE OF PLAYER 2
specified:-ai(X).
control(2):-not specified.

control(X):-ai(X).
