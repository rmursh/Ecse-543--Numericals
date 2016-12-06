function f =  integration(a, b, n)
segLen = (b-a)/n;
runningSum = 0;
for i =1:n
    runningSum = runningSum + sin(segLen*((i-1)+0.5));
end
f  = runningSum * segLen;