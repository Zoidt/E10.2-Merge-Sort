/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

import java.util.Arrays;
import java.util.Scanner;

import static ansi.ANSI.*;

/**
 * Implementation of Mergesort
 */
public class PrettyMergeSort {

	private static final Scanner stdin;

	static {
		stdin = new Scanner(System.in);
	}

	private static void pressEnterToContinue() {
		System.out.print("Press enter to continue...");
		System.out.flush();
		stdin.nextLine();
	}

	public static void main(String[] args) {
		Integer[] values = { 8, 6, 3, 4, 2, 9, 1, 5, 7 };

		System.out.println("Unsorted array:");
		printArray(values);
		System.out.println();

		sort(values);

		System.out.println("Sorted array:");
		printArray(values);
		System.out.println();
	}

	public static <T> void printArray(T[] values) {
		Color[] whites = new Color[values.length];
		Arrays.fill(whites, Color.NONE);
		printArray(values, whites);
	}

	public static <T> void printArray(T[] values, Color[] colors) {
		if (colors.length != values.length) {
			throw new IllegalArgumentException();
		}

		StringBuilder border = new StringBuilder();
		StringBuilder content = new StringBuilder();

		border.append("|-");
		content.append("| ");
		for (int i = 0; i < values.length; i++) {
			String current = values[i] == null ? " " : values[i].toString();
			for (int j = 0; j < current.length(); j++) {
				border.append('-');
			}
			content.append(color(colors[i], current));
			if (i < values.length - 1) {
				border.append("-+-");
				content.append(" | ");
			}
		}
		border.append("-|");
		content.append(" |");

		System.out.println(border.toString());
		System.out.println(content.toString());
		System.out.println(border.toString());
	}

	public static <T extends Comparable<T>> void sort(T[] elements) {
		// create data buffer to optimize recursion
		T[] buffer = (T[]) new Comparable[elements.length];

		// call mergesort
		sort(elements, 0, elements.length - 1, buffer, 1);
	}

	/**
	 * Mergesort elements between left and right
	 *
	 * @param elements elements to be sorted.
	 * @param left     the leftmost index to sort.
	 * @param right    the rightmost index to sort.
	 * @param buffer   the buffer to use during sorting.
	 */
	private static <T extends Comparable<T>> void sort(T[] elements, int left, int right, T[] buffer, int depth) {
		System.out.printf("*".repeat(depth) + " mergesort(elements, %d, %d, buffer)\n", left, right);

		Color[] startColours = new Color[elements.length];
		Arrays.fill(startColours, Color.BLACK);

		for (int i = left; i <= right; i++) {
			startColours[i] = Color.NONE;
		}

		System.out.println("elements:");
		printArray(elements, startColours);
		pressEnterToContinue();

		// sort until single cell or empty sub-array
		if (left >= right) {
			System.out.println("nothing to do!");
			pressEnterToContinue();
			return;
		}

		// sort two sub-arrays
		int mid = (left + right) / 2;
		sort(elements, left, mid, buffer, depth + 1);
		sort(elements, mid + 1, right, buffer, depth + 1);

		// copy sorted sub-arrays into a buffer
		for (int i = left; i <= right; i++) {
			buffer[i] = elements[i];
		}

		// merge back into the element array
		merge(buffer, left, mid, right, elements, depth + 1);

		pressEnterToContinue();
	}

	/**
	 * Merge contiguous sub-arrays of one array (source) into a second array (destination),
	 * specifically: left to mid (inclusive) and mid+1 to right (inclusive)
	 *
	 * @param source The source array.
	 * @param left The left-most sub-array index.
	 * @param mid The midpoint index of the sub-arrays.
	 * @param right The right-most sub-array index.
	 * @param destination The destination array.
	 */
	private static <T extends Comparable<T>> void merge(T[] source, int left, int mid, int right, T[] destination, int depth) {
		System.out.printf("*".repeat(depth) + " merge(source, %d, %d, %d, destination)\n", left, mid, right);
		pressEnterToContinue();

		int i = left;
		int j = mid + 1;
		int k = left;

		Color[] sourceColours = new Color[source.length];
		Arrays.fill(sourceColours, Color.BLACK);

		Color[] destinationColours = new Color[destination.length];
		Arrays.fill(destinationColours, Color.BLACK);

		// while there are elements in both sub-arrays to merge
		while ((i <= mid) && (j <= right)) {
			// take the smaller of the two
			if (source[i].compareTo(source[j]) <= 0) {
				sourceColours[i] = Color.GREEN;
				destinationColours[k] = Color.GREEN;
				destination[k++] = source[i++];
			}
			else {
				sourceColours[j] = Color.PURPLE;
				destinationColours[k] = Color.PURPLE;
				destination[k++] = source[j++];
			}
		}

		// merge the remaining element directly
		if (i > mid) {
			while (j <= right) {
				sourceColours[j] = Color.PURPLE;
				destinationColours[k] = Color.PURPLE;
				destination[k++] = source[j++];
			}
		}
		else {
			while (i <= mid) {
				sourceColours[i] = Color.GREEN;
				destinationColours[k] = Color.GREEN;
				destination[k++] = source[i++];
			}
		}

		System.out.println("source:");
		printArray(source, sourceColours);

		System.out.println("destination:");
		printArray(destination, destinationColours);
	}

}
