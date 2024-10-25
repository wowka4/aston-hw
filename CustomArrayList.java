import java.util.*;

public class CustomArrayList<T> {
    private static final double INCREASE_RATIO = 1.5;

    private T[] arr = (T[]) new Object[10];
    private int size = 0;

    public void add(T element) {
        increaseArray();
        arr[size] = element;
        size++;
    }

    public int length() {
        return size;
    }

    public void addAll(Collection<? extends T> c) {
        if (arr.length - size < c.size()) {
            arr = Arrays.copyOf(arr, size + c.size());
        }
        for (T el : c) {
            arr[size] = el;
            size++;
        }
    }


    public void clear() {
        arr = (T[]) new Object[10];
        size = 0;
    }

    public T get(int index) {
        checkIndex(index);
        return arr[index];
    }

    public boolean isEmpty() {
        return size < 0;
    }

    public T remove(int index) {
        checkIndex(index);
        T element = arr[index];
        System.arraycopy(arr, index + 1, arr, index, size - index);
        size--;
        return element;
    }

    public T remove(Object o) {
        T element = (T) o;
        int index = -1;
        T elem = null;
        for (int i = 0; i <= size; i++) {
            if (arr[i].equals(element)) {
                elem = arr[i];
                index = i;
                break;
            }
        }
        System.arraycopy(arr, index + 1, arr, index, size - index);
        size--;
        return elem;
    }

    public void sort(Comparator<? super T> c) {
        if (size == 0) {
            return;
        }

        mergeSort(arr, c);
    }

    private void mergeSort(T[] array, Comparator<? super T> c) {
        if (array == null || array.length < 2) {
            return;
        }
        T[] tempArray = array.clone();
        mergeSort(array, tempArray, 0, array.length - 1, c);
    }

    private void mergeSort(T[] array, T[] tempArray, int left, int right, Comparator<? super T> c) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(array, tempArray, left, middle, c);
            mergeSort(array, tempArray, middle + 1, right, c);

            merge(array, tempArray, left, middle, right, c);
        }
    }

    private void merge(T[] array, T[] tempArray, int left, int middle, int right, Comparator<? super T> c) {
        System.arraycopy(array, left, tempArray, left, right - left + 1);

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (tempArray[i] == null) {
                array[k++] = tempArray[j++];
            } else if (tempArray[j] == null) {
                array[k++] = tempArray[i++];
            } else if (c.compare(tempArray[i], tempArray[j]) <= 0) {
                array[k++] = tempArray[i++];
            } else {
                array[k++] = tempArray[j++];
            }
        }

        while (i <= middle) {
            array[k++] = tempArray[i++];
        }
    }

    private void increaseArray() {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, (int) (arr.length * INCREASE_RATIO + 1));
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}

