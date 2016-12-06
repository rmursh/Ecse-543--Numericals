function d = calculateSlopeInternal(h,delta)
n = length(h)+1;
d = zeros(size(h));
k = find(sign(delta(1:n-2)).*sign(delta(2:n-1))>0)+1;
w1 = 2*h(k)+h(k-1);
w2 = h(k)+2*h(k-1);
d(k) = (w1+w2)./(w1./delta(k-1) + w2./delta(k));
% Slopes at endpoints
d(1) = calculateSlopeEnd(h(1),h(2),delta(1),delta(2));
d(n) = calculateSlopeEnd(h(n-1),h(n-2),delta(n-1),delta(n-2));

function d = calculateSlopeEnd(h1,h2,del1,del2)
d = ((2*h1+h2)*del1 - h1*del2)/(h1+h2);
if sign(d) ~= sign(del1)
d = 0;
elseif (sign(del1)~=sign(del2))&&(abs(d)>abs(3*del1))
d = 3*del1;
end