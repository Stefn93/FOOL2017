push 0
push A
lhp
sw
push 1
lhp
add
shp
push 2
lhp
sw
push 1
lhp
add
shp
lhp
lhp
sop
lhp
sw
push 1
lhp
add
shp
push B
lhp
sw
push 1
lhp
add
shp
push 3
lhp
sw
push 1
lhp
add
shp
lhp
lhp
sop
push F
lhp
sw
push 1
lhp
add
shp
push 2
lhp
sw
push 1
lhp
add
shp
lhp
lhp
sop
lop
sro
push -2
lfp
add
lw
sop
lfp
push -3
lfp
add
lw
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

foo3A1:
cfp
lra
push 9
srv
sra
pop
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
beq foo3A1

B:
lmo
push 0
beq foo3A1

C:
lmo
push 0
beq foo3A1

foo3F1:
cfp
lra
push 6
srv
sra
pop
pop
sfp
lrv
lra
lro
sop
js

F:
lmo
push 0
beq foo3F1
halt
