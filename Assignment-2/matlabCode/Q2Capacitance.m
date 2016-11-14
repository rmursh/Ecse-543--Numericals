nodesWide = 6;
nodesHigh = 6;

mesh = zeros(nodesWide, nodesHigh);
input = zeros(4, 34);
% read mesh values
file = fopen('Simple2DOutput.txt', 'r');
input = fscanf(file, '%f', size(input));
input = input';
fclose(file);


for x = 1: size(input, 1)
    xNode = cast((input(x,2) / 0.02), 'int8');
    yNode = cast((input(x,3) / 0.02), 'int8');
    mesh(yNode + 1,xNode + 1) = input(x,4);
end
	
% % voltage is 10 volts on inner conductor
mesh(1,1) = 110.0;
mesh(1,2) = 110.0;

totalEnergy = 0.0;
for y  = 1 : (nodesHigh - 1);
	for x = 1: (nodesWide - 1);
		u1 = mesh(y+1,x);
		u2 = mesh(y,x);
		u3 = mesh(y,x+1);
		u4 = mesh(y+1,x+1);
		
		totalEnergy = totalEnergy + (u1*u1 - u1*u2);
		totalEnergy = totalEnergy + (-u1*u4 + u2*u2);
		totalEnergy = totalEnergy + (-u2*u3 + u3*u3);
		totalEnergy = totalEnergy + (-u3*u4 + u4*u4);
    end
end

% 		
epsilon = 8.854187817620e-12;
voltageSquared = 12100;
Capacitance = totalEnergy*(epsilon * 4 / voltageSquared);
% 
