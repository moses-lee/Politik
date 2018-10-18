package com.wordpress.necessitateapps.politik.Services;

public class ArticleBias {
//    0 = left
//    1 = left-center
//    2 = center
//    3 = right-center
//    4 = right

    public int getBias(String source) {

        int biasScore = 6;

        source = source.replaceAll("\\s+","").toLowerCase();

        if (source.contains("cnn") || source.contains("huffingtonpost") || source.contains("vanityfair") || source.contains("vox") || source.contains("newyorker"))
            biasScore = 0;
        if (source.contains("washingtonpost") || source.contains("latimes") || source.contains("newyorktimes") || source.contains("npr") || source.contains("politico"))
            biasScore = 1;
        if (source.contains("c-span") || source.contains("pewresearch") || source.contains("reuters") || source.contains("theeconomist") || source.contains("wikipedia"))
            biasScore = 2;
        if (source.contains("chicagotribune") || source.contains("forbes") || source.contains("newyorkpost") || source.contains("wallstreetjournal") || source.contains("wikileaks"))
            biasScore = 3;
        if (source.contains("dailymail") || source.contains("drudgereport") || source.contains("foxnews") || source.contains("nationalrifleassociation") || source.contains("theblaze"))
            biasScore = 4;

        return biasScore;
    }


}
