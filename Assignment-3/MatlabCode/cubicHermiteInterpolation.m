function v = cubicHermiteInterpolation(x,y,u)
h = diff(x);
delta = diff(y)./h;
d = calculateSlopeInternal(h,delta);
% Piecewise polynomial coefficients
n = length(x);
c = (3*delta - 2*d(1:n-1) - d(2:n))./h;
b = (d(1:n-1) - 2*delta + d(2:n))./h.^2;
% Find subinterval indices k so that x(k) <= u < x(k+1)
k = ones(size(u));
for j = 2:n-1
k(x(j) <= u) = j;
end
% Evaluate interpolant
s = u - x(k);
v = y(k) + s.*(d(k) + s.*(c(k) + s.*b(k)));