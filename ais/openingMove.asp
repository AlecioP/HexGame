% case control(2) LEFT to RIGHT

pieRule :- control(2), cell(1,N,_), N=1.
pieRule :- control(2), cell(1,N,_), N1=#max{Row:cell(_,Row,_)},N=N1-1.

pieRule :- control(2), cell(1,0,0).
pieRule :- control(1), cell(1,N,N), N=#max{Row:cell(_,Row,_)}.

response(R,1)|noResponse(R,1):-control(2), cell(0,R,1), not pieRule.


% case control(1) TOP to BOTTOM

pieRule :- control(1), cell(2,_,N), N=1.
pieRule :- control(1), cell(2,_,N), N1=#max{Row:cell(_,Row,_)},N=N1-1.

pieRule :- control(1), cell(2,0,0).
pieRule :- control(1), cell(2,N,N), N=#max{Row:cell(_,Row,_)}.

response(1,C)|noResponse(1,C):-control(1), cell(0,1,C), not pieRule.

%general rule

:- 1 <> #count{X,Y:response(X,Y)}.
