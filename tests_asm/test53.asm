push 0
push A
lhp
push 0
add
sw
push 2
lhp
push 1
add
sw
lhp
push 2
add
shp
lhp
push B
lhp
push 0
add
sw
push 2
lhp
push 1
add
sw
lhp
push 2
add
shp
lhp
push C
lhp
push 0
add
sw
push 2
lhp
push 1
add
sw
lhp
push 2
add
shp
lhp
push D
lhp
push 0
add
sw
push 2
lhp
push 1
add
sw
lhp
push 2
add
shp
lhp
push function0
lfp
lfp
push -6
lfp
add
lw
js
lop
sro
push -7
lfp
add
lw
sop
lfp
lfp
push 0
smo
lop
push -2
add
lw
js
print
halt

f1A1:
cfp
lra
push 44
srv
sra
pop
sfp
lrv
lra
lro
sop
js

A:
lmo
push 0
beq f1A1

B:
lmo
push 0
beq f1A1

D:
lmo
push 0
beq f1A1

C:
lmo
push 0
beq f1A1

function0:
cfp
lra
push -3
lfp
lw
add
lw
srv
sra
pop
sfp
lrv
lra
js
halt
