package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimilarityFinderTest {

    @Test
    void testEmptyArrays() {
        int[] array = {};
        int[] array2 = {};
        SequenceSearcher sequenceSearcher = (elem, sequence) -> SearchResult.builder().build();
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1,finder.calculateJackardSimilarity(array, array2));
    }

    @Test
    void testEqualsArrays(){
        int[] array = {768, 1232, 9123, 50};
        int[] array2 = {768, 1232, 9123, 50};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                if(elem == 768)
                    searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
                else if(elem == 1232)
                    searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
                else if(elem == 9123)
                    searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
                else if(elem == 50)
                    searchResult = SearchResult.builder().withPosition(1).withFound(true).build();

                return searchResult;
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1,finder.calculateJackardSimilarity(array, array2));
    }


    @Test
    void testCompletelyDifferentArrays(){
        int[] array = {0, 2354, 100, 700};
        int[] array2 = {678, 2, 9120, 32};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                if(elem == 0)
                    searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
                else if(elem == 2354)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                else if(elem == 100)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                else if(elem == 700)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                return searchResult;
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(0,finder.calculateJackardSimilarity(array, array2));
    }

    @Test
    void testDifferentArrays(){
        int[] array = {34, 55};
        int[] array2 = {5, 34};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                if(elem == 34)
                    searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
                else if(elem == 55)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                return searchResult;
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1.0/3.0,finder.calculateJackardSimilarity(array, array2));
    }

}
