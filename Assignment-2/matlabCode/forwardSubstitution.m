function x=forwardSubstitution(L,b,n)

x=zeros(n,1);
for j=1:n
    if (L(j,j)==0) error('Matrix is singular!'); end;
    x(j)=b(j)/L(j,j);
    b(j+1:n)=b(j+1:n)-L(j+1:n,j)*x(j);
end 