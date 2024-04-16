package sortingvisualizer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


class GraphicElement {
    ImageIcon image;
    int size;

    public GraphicElement(ImageIcon image, int size) {
        this.image = image;
        this.size = size;
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getSize() {
        return size;
    }
}

public class SortingVisualizer {
    private JFrame mainFrame;
    private JComboBox<String> sortSelector;
    private JButton sortButton, resetButton;
    private JPanel visualizationPanel;
    private final List<GraphicElement> elements = new ArrayList<>();

    public SortingVisualizer() {
        initializeGUI();
        populateElements();
    }

    private void initializeGUI() {
        mainFrame = new JFrame("Sorting Visualizer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        String[] algorithms = {"Bubble sort", "Selection sort", "Insertion sort", "Merge sort", "Quick sort", "Heap sort"};
        sortSelector = new JComboBox<>(algorithms);
        mainFrame.add(sortSelector, BorderLayout.NORTH);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) sortSelector.getSelectedItem();
                switch (selectedAlgorithm) {
                    case "Bubble sort":
                        bubbleSort();
                        break;
                    case "Selection sort":
                        selectionSort();
                        break;
                    case "Insertion sort":
                        insertionSort();
                        break;
                    case "Merge sort":
                        mergeSort();
                        break;
                    case "Quick sort":
                        quickSort(0, elements.size() - 1);
                        break;
                    case "Heap sort":
                        heapSort();
                        break;
                }
            }
        });

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetAndShuffle();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(sortButton);
        buttonPanel.add(resetButton);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int fillerWidth = screenSize.width / 4;
        JPanel fillerLeft = new JPanel();
        fillerLeft.setOpaque(false);
        fillerLeft.setPreferredSize(new Dimension(fillerWidth, 0));
        JPanel fillerRight = new JPanel();
        fillerRight.setOpaque(false);
        fillerRight.setPreferredSize(new Dimension(fillerWidth, 0));
        bottomPanel.add(fillerLeft, BorderLayout.WEST);
        bottomPanel.add(fillerRight, BorderLayout.EAST);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        visualizationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawElements(g);
            }
        };
        visualizationPanel.setPreferredSize(new Dimension(1250, 550));
        mainFrame.add(visualizationPanel, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void resetAndShuffle() {
        Collections.shuffle(elements);
        repaintPanel();
    }

    private void populateElements() {
        String[] imageFiles = {"image1.png", "image2.png", "image3.png", "image4.png", "image5.png", "image6.png", "image7.png", "image8.png"};
        List<GraphicElement> tempElements = new ArrayList<>();
        for (String fileName : imageFiles) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imgs/" + fileName));
            int size = icon.getIconHeight();
            tempElements.add(new GraphicElement(icon, size));
        }
        Collections.shuffle(tempElements);
        elements.addAll(tempElements);
    }


    private void drawElements(Graphics g) {
        int panelHeight = visualizationPanel.getHeight();
        int startX = 10;
        for (GraphicElement element : elements) {
            Image image = element.getImage().getImage();
            int imageWidth = element.getImage().getIconWidth();
            int imageHeight = element.getImage().getIconHeight();
            int startY = (panelHeight / 2) - imageHeight;
            g.drawImage(image, startX, startY, imageWidth, imageHeight, null);
            startX += imageWidth + 10;
        }
    }


    private void bubbleSort() {

        Runnable task = new Runnable() {

            int i = 0, j = 0;


            public void run() {
                if (i < elements.size() - 1) {
                    if (j < elements.size() - i - 1) {
                        if (elements.get(j).getSize() > elements.get(j + 1).getSize()) {
                            Collections.swap(elements, j, j + 1);
                            repaintPanel();
                        }
                        j++;
                        sleepForAWhile(700, this);
                    } else {
                        j = 0;
                        i++;
                        sleepForAWhile(700, this);
                    }
                }
            }
        };
        sleepForAWhile(700,task);
    }

    private void selectionSort() {
        Runnable task = new Runnable() {
            int i = 0, j = i + 1, min_idx = i;
            public void run() {
                if (i < elements.size() - 1) {
                    if (j < elements.size()) {
                        if (elements.get(j).getSize() < elements.get(min_idx).getSize()) {
                            min_idx = j;
                        }
                        j++;
                        sleepForAWhile(100, this);
                    } else {
                        if (min_idx != i) {
                            Collections.swap(elements, i, min_idx);
                            repaintPanel();
                        }
                        i++;
                        j = i + 1;
                        min_idx = i;
                        sleepForAWhile(100, this);
                    }
                }
            }
        };
        sleepForAWhile(100, task);
    }

    private void insertionSort() {
        Runnable task = new Runnable() {
            int i = 1, j = i - 1;
            GraphicElement key = elements.get(i);
            public void run() {
                if (i < elements.size()) {
                    if (j >= 0 && elements.get(j).getSize() > key.getSize()) {
                        elements.set(j + 1, elements.get(j));
                        j--;
                        sleepForAWhile(100, this);
                    } else {
                        elements.set(j + 1, key);
                        i++;
                        if (i < elements.size()) {
                            key = elements.get(i);
                            j = i - 1;
                        }
                        sleepForAWhile(100, this);


                    }
                    repaintPanel();
                }
            }
        };
        sleepForAWhile(100, task);

    }

    public void mergeSort() {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0, elements.size() - 1});

        Runnable mergeSortTask = new Runnable() {
            public void run() {
                boolean continueSorting = false;

                while (!stack.isEmpty() && !continueSorting) {
                    int[] range = stack.pop();
                    if (range.length == 3) {
                        // Merge op.
                        merge(range[0], range[1], range[2]);
                        repaintPanel();
                    } else {
                        int left = range[0];
                        int right = range[1];
                        if (left < right) {
                            int middle = (left + right) / 2;
                            stack.push(new int[]{left, middle, right});
                            stack.push(new int[]{middle + 1, right});
                            stack.push(new int[]{left, middle});
                            continueSorting = true;
                        }
                    }
                }

                if (continueSorting) {




                    sleepForAWhile(100, this);
                }
            }
        };




        sleepForAWhile(100, mergeSortTask);
    }

    private void merge(int left, int middle, int right) {

        List<GraphicElement> temp = new ArrayList<>();
        int i = left, j = middle + 1;

        while (i <= middle && j <= right) {
            if (elements.get(i).getSize() <= elements.get(j).getSize()) {
                temp.add(elements.get(i));
                i++;
            } else {

                temp.add(elements.get(j));
                j++;
            }
        }

        while (i <= middle) {

            temp.add(elements.get(i));
            i++;
        }
        while (j <= right) {

            temp.add(elements.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            elements.set(left + k, temp.get(k));
        }
    }



    private void quickSort(int low, int high) {

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{low, high});


        Runnable task = new Runnable() {
            @Override
            public void run() {


                if (!stack.isEmpty()) {
                    int[] range = stack.pop();
                    int start = range[0];

                    int end = range[1];
                    if (start < end) {

                        int pi = partition(start, end);
                        stack.push(new int[]{start, pi - 1});

                        stack.push(new int[]{pi + 1, end});
                    }
                    if (!stack.isEmpty()) {
                        sleepForAWhile(100, this);
                    }
                }
            }
        };
        sleepForAWhile(100, task);
    }

    private void heapSort() {
        int n = elements.size();

        Runnable heapSortTask = new Runnable() {
            int i = n / 2 - 1;
            int k = n - 1;

            @Override
            public void run() {
                if (i >= 0) {

                    heapify(elements, n, i);
                    i--;
                    repaintPanel();
                    sleepForAWhile(100, this);


                } else if (k > 0) {


                    Collections.swap(elements, 0, k);
                    heapify(elements, k, 0);
                    k--;
                    repaintPanel();
                    sleepForAWhile(100, this);
                }
            }
        };

        sleepForAWhile(100, heapSortTask);
    }

    private void heapify(List<GraphicElement> arr, int n, int i) {

        int largest = i;
        int left = 2 * i + 1;

        int right = 2 * i + 2;





        if (left < n && arr.get(left).getSize() > arr.get(largest).getSize())
            largest = left;
        if (right < n && arr.get(right).getSize() > arr.get(largest).getSize())
            largest = right;
        if (largest != i) {
            Collections.swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }




    private int partition(int low, int high) {
        GraphicElement pivot = elements.get(high);

        int i = (low - 1);





        for (int j = low; j < high; j++) {
            if (elements.get(j).getSize() < pivot.getSize()) {
                i++;
                Collections.swap(elements, i, j);
            }
        }
        Collections.swap(elements, i + 1, high);


        repaintPanel();





        return i + 1;
    }

    private void repaintPanel() {

        visualizationPanel.repaint();
    }

    private void sleepForAWhile(int milliseconds, Runnable actionAfterDelay) {
        Timer timer = new Timer(milliseconds, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionAfterDelay.run();



            }

        });


        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortingVisualizer::new);
    }
}
