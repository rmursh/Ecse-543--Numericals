function y = lasolve(a, c, g )
% Symbolic solution of linear differential equation by Laplace transforms
% LASOLVE (a, c, 'f(t)')
% a is a polynomial coefficient vector to differentiation operator
% G[y] = a(1)y^(n-1) + a(2)y^(n-2) + ... + a(n-1)y' + a(n)y
% c is a vector of initial conditions.
% c(1) = y(0); c(2) = y'(0); ... ; c(n-1) = y^(n-1)(0).
% g is a right-hand member as function of t.
%
%
% Examples:
%
% lasolve([1 0 1], [1 0], exp(t))
%

syms s t

yla = laplace(g, s) / poly2sym(a, s);

for i=1:length(c)
    yla = yla + c(i)*poly2sym(a(1:end-i), s) / poly2sym(a, s);
end

y = simplify(ilaplace(yla, t));

%END of lasolve.m