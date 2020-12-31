package kata

object Cryptarithm {
    private val allDigits = (0..9).toList()

    fun alphametics(puzzle: String): String {
        val words = puzzle.split(" ").filter { it.toCharArray().all { l -> l.isLetter() } }
        val firstChars = words.map { it.first() }
        val numLetters = words.flatMap { it.toCharArray().toList() }.distinct().size

        fun isValid(assignment: Map<Char, Int>) =
            words.dropLast(1).map { transform(it, assignment).toInt() }.sum() == transform(
                words.last(),
                assignment
            ).toInt()

        fun search(idx: Int, assignment: Map<Char, Int>, carry: Int): Map<Char, Int>? {
            val charsAtIdx = words.map { it.toCharArray() }.filter { it.size > idx }.map { it[it.size - idx - 1] }
            if (numLetters == assignment.size) return if (isValid(assignment)) assignment else null
            val candidates = solve(charsAtIdx, assignment, carry).filter { firstChars.none { c -> it.first[c] == 0 } }
            for (candidate in candidates) {
                val result = search(idx + 1, assignment + candidate.first, candidate.second)
                if (result != null) return result
            }
            return null
        }
        val initialAssignments = mutableMapOf<Char, Int>()
        val lastChars = words.map { it.last() }.distinct()
        if (lastChars.size == 1) initialAssignments[lastChars[0]] = 0
        if (words.size == 2 && words.last().length > words.dropLast(1).map { it.length }.maxOrNull()!!){
            initialAssignments[words.last()[0]] = 1
        }
        return transform(puzzle, search(0, initialAssignments, 0)!!)
    }

    private fun transform(s: String, assignments: Map<Char, Int>) =
        s.toCharArray().map { assignments[it]?.toString() ?: it }.joinToString("")

    private fun solve(letters: List<Char>, assignment: Map<Char, Int>, carry: Int): List<Pair<Map<Char, Int>, Int>> {
        val unassignedLetters = letters.distinct().filter { !assignment.keys.contains(it) }
        if (unassignedLetters.isEmpty()) {
            val sum = letters.dropLast(1).map { assignment[it]!! }.sum() + carry
            return if (sum % 10 == assignment[letters.last()]!!) listOf(Pair(assignment, sum / 10)) else listOf()
        }
        return allDigits
            .filter { !assignment.values.contains(it) }
            .combinations(unassignedLetters.size)
            .filter { it.none { d -> it.count { od -> od == d } > 1 } }
            .map { cand -> letters.map { assignment[it] ?: cand[unassignedLetters.indexOf(it)] } }
            .filter { (it.dropLast(1).sum() + carry) % 10 == it.last() }
            .map {
                Pair(assignment + letters.zip(it).toMap(), (it.dropLast(1).sum() + carry) / 10)
            }
    }
}

private fun List<Int>.combinations(n: Int): List<List<Int>> {
    if (n == 0) return listOf()
    fun perm(a: List<Int>, result: List<Int>, l: Int): MutableList<List<Int>> {
        if (result.size == l) return mutableListOf(result)
        val combinations = mutableListOf<List<Int>>()
        for (i in a.indices) {
            val nr = result + a[i]
            combinations.addAll(perm(a, nr, l))
        }
        return combinations
    }
    return perm(this, listOf(), n)
}
