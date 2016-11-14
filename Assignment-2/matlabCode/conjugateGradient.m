CABLE_HEIGHT = 0.1;
CABLE_WIDTH = 0.1;
CORE_HEIGHT = 0.02;
CORE_WIDTH = 0.04;
CORE_POTENTIAL = 110;
MIN_RESIDUAL = 0.0001;

height  = 0.02;

nodesWide = int8(CABLE_WIDTH/height) + 1;
nodesHigh = int8(CABLE_HEIGHT/height) + 1;
nodeNumber = 19;

mesh = zeros(nodesWide,nodesHigh);

[rows, columns] = size(mesh);
for i = 1:rows 
 for j = 1:columns
    if(( j <= int16(CORE_WIDTH/height)+1)&&(i <= int16(CORE_HEIGHT/height)+1))
       mesh(i,j) = CORE_POTENTIAL;
    else
       mesh(i,j) = 0;
    end       
 end
end

%Constructing A and B matrices 
B = zeros([nodeNumber 1]);
B(1) = -110;
B(4) = -110;
B(7) = -110;
B(10) = -110;
B(11) = -110;

A = zeros(nodeNumber) - [[4 -1 0 -2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
     [- 1 4 -1 0 -2 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
     [0 -1 4 0 0 -2 0 0 0 0 0 0 0 0 0 0 0 0 0]
     [- 1 0 0 4 -1 0 -1 0 0 0 0 0 0 0 0 0 0 0 0]
     [0 -1 0 -1 4 -1 0 -1 0 0 0 0 0 0 0 0 0 0 0]
     [0 0 -1 0 -1 4 0 0 -1 0 0 0 0 0 0 0 0 0 0]
     [0 0 0 -1 0 0 4 -1 0 0 0 -1 0 0 0 0 0 0 0]
     [0 0 0 0 -1 0 -1 4 -1 0 0 0 -1 0 0 0 0 0 0]
     [0 0 0 0 0 -1 0 -1 4 0 0 0 0 -1 0 0 0 0 0]
     [0 0 0 0 0 0 0 0 0 4 -2 0 0 0 -1 0 0 0 0]
     [0 0 0 0 0 0 0 0 0 -1 4 -1 0 0 0 -1 0 0 0]
     [0 0 0 0 0 0 -1 0 0 0 -1 4 -1 0 0 0 -1 0 0]
     [0 0 0 0 0 0 0 -1 0 0 0 -1 4 -1 0 0 0 -1 0]
     [0 0 0 0 0 0 0 0 -1 0 0 0 -1 4 0 0 0 0 -1]
     [0 0 0 0 0 0 0 0 0 -1 0 0 0 0 4 -2 0 0 0]
     [0 0 0 0 0 0 0 0 0 0 -1 0 0 0 -1 4 -1 0 0]
     [0 0 0 0 0 0 0 0 0 0 0 -1 0 0 0 -1 4 -1 0]
     [0 0 0 0 0 0 0 0 0 0 0 0 -1 0 0 0 -1 4 -1]
     [0 0 0 0 0 0 0 0 0 0 0 0 0 -1 0 0 0 -1 4]
     ];
 %Cannot do this since it gives compleX values
 %decomposedA = choleskYDecompose(A*A');

%Solving using Cholesky
%Solution is to multiplY bY transpose
newA = A' * A;
U = choleskyDecompose(newA);
B = A'*B;
Ut = U';
%Solve the equation
Y = zeros([size(B) 1]);
X = zeros([size(B) 1]);
% Acuiring D vector
n = length(U);
% Now use a vector y to solve 'Ly=b' 
Y=forwardSubstitution(U,B,n);
X=backSubstitution(Ut,Y, n);
XCholesky = X;

%Solving using conjugate gradient
X = zeros([nodeNumber 1]);
R = B-(newA*X);
P = R;
infNormVec = []; 
twoNormVec = [];
iterations = [];
results = zeros(2, nodeNumber);
for i = 1:nodeNumber
    temp = (P'*R);
    temp1 = (P'*newA*P);
    alpha = temp/temp1;
    X = X + alpha*P;
    R = B - newA*X;
    temp = (P'*newA*R);
    beta = ((-1)*temp)/(temp1);
    P = R + beta*P;
    
   results(1,1) = X(1);
   results(2,1)= R(1);
    for y  = 1 : nodeNumber - 1
		results(1,y+1) = X(y+1);
        results(2,y+1)= R(y+1);
    end
    infNorm = 0;
    twoNorm = 0;
    for y  = 1 : nodeNumber
        val = abs(R(y,1));
        if val > infNorm
            infNorm = val;
        end
        twoNorm = twoNorm + (R(y,1))^2;
    end
    twoNorm = sqrt(twoNorm);
    infNormVec = [infNormVec, infNorm];
    twoNormVec = [twoNormVec, twoNorm];
    iterations = [iterations,i];
end

figure 
plot(iterations, infNormVec);
figure 
plot(iterations, twoNormVec);


