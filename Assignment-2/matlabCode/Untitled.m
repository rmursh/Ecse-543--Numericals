outputFile = fopen('meshGen.txt', 'w');

numberHorNodes = 6;
numberVerNodes = 5;

%mesh = zeros(numberHorNodes,numberHorNodes);
for y = 1:numberVerNodes
    for x = 1:(numberHorNodes+1)
        mesh(y,x) = (x-1) + (y-1)*numberHorNodes;
    end
end


for y = 1:numberVerNodes
    for x = 1: numberHorNodes
        fprintf(outputFile,'%f %f \n',[(x-1)*0.02 (y)*0.02]);
    end
end
for x = 2:5
    fprintf(outputFile,'%f %f \n',[x*0.02 0]);
end
fprintf(outputFile,'/\n');

for y = 1:numberVerNodes-1
    for x = 2:numberHorNodes
        fprintf(outputFile, '%i %i %i 0.0000 \n', [mesh(y, x) mesh(y,x+1) mesh(y+1,x)]);
    end
end

for y = 2: numberVerNodes
    for x = 2 : numberHorNodes
        fprintf(outputFile, '%i %i %i 0.0000 \n', [mesh(y, x) mesh(y-1,x+1) mesh(y,x+1)]);
    end
end

fprintf(outputFile,'/\n');

for x = 2 : numberHorNodes + 1
    fprintf(outputFile, '%i 0.000 \n', mesh(numberVerNodes, x));
end 
for y = 2 : numberVerNodes - 1 
    fprintf(outputFile, '%i 0.000 \n', mesh(y, numberHorNodes));
end 
fclose(outputFile);