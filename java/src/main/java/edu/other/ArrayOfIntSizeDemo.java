package edu.other;

class ArrayOfIntSizeDemo {
    public static void main(String[] args) {
        int size = 4 * (int) Math.pow(10, 7);
        /*
         * Размер массива array =
         * 8 байт (ссылка на массив в 64-битной системе)
         * + 4 байта (хранится размер массива)
         * + 4 байта (выравнивание до 8 байт суммы (ссылка на массив и размер массива)
         * + 4 * 10^7 (число элементов) * 4 байта (размер int)
         * = 160 000 016 байт
         * = 156 250,015625 килобайт
         * = 152,5879058837890625 мегабайт
         * ~ 152,6 мегабайт
         */
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = i;

        //для дальнейшего анализа процесса jvm в Eclipse Memory Analyzer
        while (true) {
        }
    }
}
