package com.cn.util.jiaMi_jieMi.kaiser;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 频率分析法破解凯撒密码
 * @author mu_qingfeng
 * @date 2020/6/13 7:16 PM
 */
public class FrequencyAnalysis {
    //英文里出现次数最多的字符
    private static final char MAGIC_CHAR = 'e';
    //破解生成的最大文件数
    private static final int DE_MAX_FILE = 4;

    public static void main(String[] args) throws Exception {
        //测试1，统计字符个数
//        printCharCount("article.txt");

        //测试2，加密文件
        int key = 3;
//        encryptFile("article.txt", "article_en.txt", key);

        //测试3，统计密文字符个数
//        printCharCount("article_en.txt");
        //读取加密后的文件
         String artile = Util.file2String("article_en.txt");
        //测试4，解密（会生成多个备选文件）
         decryptCaesarCode(artile, "article_de.txt");
    }

    public static void printCharCount(String path) throws IOException{
        String data = Util.file2String(path);
        List<Entry<Character, Integer>> mapList = getMaxCountChar(data);
        for (Entry<Character, Integer> entry : mapList) {
            //输出前几位的统计信息
            System.out.println("字符'" + entry.getKey() + "'出现" + entry.getValue() + "次");
        }
    }

    public static void encryptFile(String srcFile, String destFile, int key) throws IOException {
        String artile = Util.file2String(srcFile);
        //加密文件
        String encryptData = KaiserDemo.encryptKaiser(artile, key);
        //保存加密后的文件
        Util.string2File(encryptData, destFile);
    }

    /**
     * 破解凯撒密码
     * @param input 数据源
     * @return 返回解密后的数据
     */
    public static void decryptCaesarCode(String input, String destPath) {
        int deCount = 0;//当前解密生成的备选文件数
        //获取出现频率最高的字符信息（出现次数越多越靠前）
        List<Entry<Character, Integer>> mapList = getMaxCountChar(input);
        for (Entry<Character, Integer> entry : mapList) {
            //限制解密文件备选数
            if (deCount >= DE_MAX_FILE) {
                break;
            }

            //输出前几位的统计信息
            System.out.println("字符'" + entry.getKey() + "'出现" + entry.getValue() + "次");

            ++deCount;
            //出现次数最高的字符跟MAGIC_CHAR的偏移量即为秘钥
            int key = entry.getKey() - MAGIC_CHAR;
            System.out.println("猜测key = " + key + "， 解密生成第" + deCount + "个备选文件" + "\n");
            String decrypt = KaiserDemo.dencryptKaiser(input, key);

            String fileName = "de_" + deCount + destPath;
            Util.string2File(decrypt, fileName);
        }
    }

    //统计String里出现最多的字符
    public static List<Entry<Character, Integer>> getMaxCountChar(String data) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char[] array = data.toCharArray();
        for (char c : array) {
            if(!map.containsKey(c)) {
                map.put(c, 1);
            }else{
                Integer count = map.get(c);
                map.put(c, count + 1);
            }
        }

        //输出统计信息
		/*for (Entry<Character, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "出现" + entry.getValue() +  "次");
		}*/

        //获取获取最大值
        int maxCount = 0;
        for (Entry<Character, Integer> entry : map.entrySet()) {
            //不统计空格
            if (/*entry.getKey() != ' ' && */entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }

        //map转换成list便于排序
        List<Entry<Character, Integer>> mapList = new ArrayList<Entry<Character,Integer>>(map.entrySet());
        //根据字符出现次数排序
        Collections.sort(mapList, new Comparator<Entry<Character, Integer>>(){
//            @Override
            public int compare(Entry<Character, Integer> o1,
                               Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return mapList;
    }

}
