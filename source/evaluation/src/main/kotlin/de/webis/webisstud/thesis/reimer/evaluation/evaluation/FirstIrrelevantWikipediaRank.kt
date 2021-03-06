package de.webis.webisstud.thesis.reimer.evaluation.evaluation

import de.webis.webisstud.thesis.reimer.corpus.url.urls
import de.webis.webisstud.thesis.reimer.evaluation.RankingEvaluation
import de.webis.webisstud.thesis.reimer.evaluation.internal.isWikipedia
import de.webis.webisstud.thesis.reimer.ltr.JudgedRunLine
import de.webis.webisstud.thesis.reimer.model.Corpus
import de.webis.webisstud.thesis.reimer.model.trecTopics
import kotlinx.serialization.serializer


object FirstIrrelevantWikipediaRank : RankingEvaluation<Int> {

	override val serializer = Int.serializer()

	override val id = "first-irrelevant-wikipedia-rank"

	override fun evaluate(run: List<JudgedRunLine>, corpus: Corpus): Int {
		val urls = corpus.urls
		return run
				.asSequence()
				.filter { urls.isWikipedia(it.documentId) && it.relevance.judgement <= 0 }
				.map { it.runLine.position }
				.min()
				?: -1 // No Wikipedia documents in this run.
	}
}