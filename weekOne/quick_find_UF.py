class QuickFindUF:
    id = []

    def __init__(self, n):
        for i in range(0, n):
            self.id.append(i)

    def connected(self, p, q):
        return self.id[p] == self.id[q]

    def union(self, p, q):
        pid = self.id[p]
        qid = self.id[q]
        for i in range(0, len(self.id)):
            if self.id[i] == pid:
                self.id[i] = qid

    def print_id(self):
        print(self.id)
        print(len(self.id))


class QuickUnionUF:
    id = []

    def __init__(self, n):
        for i in range(0, n):
            self.id.append(i)

    def root(self, i):
        while i != self.id[i]:
            i = self.id[i]
        return i

    def connected(self, p, q):
        return self.root(p) == self.root(q)

    def union(self, p, q):
        i = self.root(p)
        j = self.root(q)
        self.id[i] = j

    def print_id(self):
        print(self.id)


# Weighting


class QuickUnionUFImp1:
    id = []
    sz = []

    def __init__(self, n):
        for i in range(0, n):
            self.id.append(i)
            self.sz.append(1)
            self.le.append(i)

    def root(self, i):
        while i != self.id[i]:
            i = self.id[i]
        return i

    def connected(self, p, q):
        return self.root(p) == self.root(q)

    def union(self, p, q):
        i = self.root(p)
        j = self.root(q)
        if i != j:
            if self.sz[i] < self.sz[j]:
                self.id[i] = j
                self.sz[j] += self.sz[i]
            else:
                self.id[j] = i
                self.sz[i] += self.sz[j]

    def print_id(self):
        print(self.id)


# Path compression: one-pass variant


class QuickUnionUFImp2:
    id = []
    sz = []

    def __init__(self, n):
        for i in range(0, n):
            self.id.append(i)
            self.sz.append(1)

    def root(self, i):
        while i != self.id[i]:
            self.id[i] = self.id[self.id[i]]
            i = self.id[i]
        return i

    def connected(self, p, q):
        return self.root(p) == self.root(q)

    def union(self, p, q):
        i = self.root(p)
        j = self.root(q)
        if i != j:
            if self.sz[i] < self.sz[j]:
                self.id[i] = j
                self.sz[j] += self.sz[i]
            else:
                self.id[j] = i
                self.sz[i] += self.sz[j]


# Interview Question 2


class QuickUnionUFWeightedWFind:
    id = []
    sz = []
    max_el = []

    def __init__(self, n):
        for i in range(0, n):
            self.id.append(i)
            self.sz.append(1)
            self.max_el.append(i)

    def root(self, i):
        while i != self.id[i]:
            i = self.id[i]
        return i

    def connected(self, p, q):
        return self.root(p) == self.root(q)

    def union(self, p, q):
        i = self.root(p)
        j = self.root(q)
        if i != j:
            if self.sz[i] < self.sz[j]:
                self.id[i] = j
                self.sz[j] += self.sz[i]
            else:
                self.id[j] = i
                self.sz[i] += self.sz[j]
            if self.max_el[i] < self.max_el[j]:
                self.max_el[i] = self.max_el[j]
            else:
                self.max_el[j] = self.max_el[i]

    def find(self, i):
        return self.max_el[self.root(i)]

    def print_id(self):
        print(self.id)
        print(self.max_el)


class SuccWDel:
    uf = None
    deleted = []

    def __init__(self, n):
        self.uf = QuickFindUF(n)
        for i in range(0, n):
            self.deleted.append(False)

    def remove(self, x):
        self.deleted[x] = True
        self.uf.union(x, x-1)

    def find_succ(self, x):
        if self.deleted[x] is False:
            return self.uf.find(x) + 1
        else:
            return None


n = int(input())
uf = QuickUnionUFWeightedWFind(n)

while input("Another?") == "":
    p = int(input())
    q = int(input())
    if not uf.connected(p, q):
        uf.union(p, q)
        print(f"{p} {q}")

print(uf.find(4))
uf.print_id()

