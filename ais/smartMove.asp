%guess one move
smartMove(X,Y)|noSmartMove(X,Y):-auxCell(0,X,Y).

:- 1 <> #count{X,Y:smartMove(X,Y)}.

%project auxCell in cell
cell(State,Row,Column):- auxCell(State,Row,Column), smartMove(Row1,Col1), Row!=Row1.
cell(State,Row,Column):- auxCell(State,Row,Column), smartMove(Row1,Col1), Column!=Col1.
cell(Color,Row,Column):- smartMove(Row,Column),control(Color).

% #include <potentialWinCalculator>
%								  %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%Now we should have 0 or 1 instances of "potentialWin"

:~ not potentialWin. [1@100]

% Answer sets with an instance of "potentialWin" are preferred

response(X,Y):- potentialWin, smartMove(X,Y).
