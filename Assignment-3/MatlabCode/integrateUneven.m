b = 1.0;
a = 0.0;
relativeWidths = [1,2,4,8,16,32,64,128,256, 512];
scale = (b-a) / sum(relativeWidths);
widths = [];
for i = 1:length(relativeWidths)
    temp = (relativeWidths(i)*scale);
    widths = [widths, temp];
end

runningWidth = 0;
runningSum = 0;
for i = 1: length(widths)
    %runningSum = runningSum + log((widths(i)/2) + runningWidth)*widths(i);
    runningSum = runningSum + log(0.2*abs((widths(i)/2) + runningWidth))*widths(i);
    runningWidth = runningWidth + widths(i);
end

f =  runningSum;
%errorUneven = abs(runningSum-(-1));
errorUneven = abs(runningSum-(q));