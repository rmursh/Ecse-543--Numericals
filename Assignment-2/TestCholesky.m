for i= 1:10
    A = generateSPDmatrix(randi([1 20]));
    DecomposedMatrix = choleskyDecompose(A);
end