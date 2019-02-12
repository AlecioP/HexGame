% case control(2) LEFT to RIGHT

pieRule :- cell(1,N,_), N=1.
pieRule :- cell(1,N,_), N1=#max{Row:cell(_,Row,_)},N=N1-1.

pieRule :- cell(1,0,0).
pieRule :- cell(1,N,N), N=#max{Row:cell(_,Row,_)}.

response(R,1)|noResponse(R,1):- cell(0,R,1), not pieRule, R>0.

% general rule

:- 1 <> #count{X,Y:response(X,Y)}.

% Implement pie rule

response(X,Y):-pieRule, cell(Value,X,Y),Value<>0.
