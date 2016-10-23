function L = choleskyDecompose(M);
% Cholesky Decompose a Symmetric Positive Definite m by m matrix
n = length( M );
L = zeros( n, n );
for i=1:n
   L(i, i) = sqrt(M(i, i) - L(i, :)*L(i, :)');
   for j=(i + 1):n
      L(j, i) = (M(j, i) - L(i,:)*L(j ,:)')/L(i, i);
   end
end
end