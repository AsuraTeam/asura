/**
 * @FileName: SynonymDictionary.java
 * @Package: com.asura.framework.tokenizer.dictionary
 * @author youshipeng
 * @created 2017/5/17 9:23
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.dictionary;

import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @version 1.0
 * @since 1.0
 */
public class SynonymDictionary {
    //    private static DoubleArrayTrie<Set<String>> trie = new DoubleArrayTrie<>();
    private static DoubleArrayTrie<Set<String>> customerTrie;
    private static ESLogger logger = Loggers.getLogger("HanLP-SynonymDictionary");
//    private static final String SYNONYM_RESOURCE_PATH = HANLP_TOKENIZER_ROOT.resolve("synonym.txt").toString();
//    private static final String SYNONYM_RESOURCE_PATH = "E:\\IdeaProject\\asura\\asura-hanlp-tokenizer\\src\\main\\resources\\synonym.txt";

    private SynonymDictionary() {
    }

    public synchronized static void reloadCustomerSynonyms(TreeMap<String, Set<String>> treeMap) {
        customerTrie = new DoubleArrayTrie<>();
        customerTrie.build(treeMap);
    }

//    private synchronized static void reload() {
//        try {
//            trie = new DoubleArrayTrie<>();
//
//            TreeMap<String, Set<String>> treeMap = new TreeMap<>();
//
//            IOUtils.read(SYNONYM_RESOURCE_PATH, line -> {
//                if (!Check.isNullOrEmpty(line)) {
//                    Set<String> words = Arrays.stream(line.split(" ")).collect(Collectors.toSet());
//                    words.forEach(word -> {
//                        if (treeMap.containsKey(word))  {
//                            treeMap.get(word).addAll(new HashSet<>(words));
//                        } else {
//                            treeMap.put(word, new HashSet<>(words));
//                        }
//                    });
//                }
//            });
//            trie.build(treeMap);
//        } catch (Exception e) {
//            throw new RuntimeException("reload config.properties error.", e);
//        }
//    }

    public static Set<String> get(String key) {
        Set<String> result = new HashSet<String>() {{
            add(key);
        }};

        try {
            return customerTrie.containsKey(key) ? customerTrie.get(key) : result;
        } catch (NullPointerException e) {
            logger.info("trie is null");
            return result;
        }
    }
}