import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        LinkedList<Integer> list=new LinkedList<>();
        list.add(1);
    }
}

class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int res = 1;
            for (int j = i; j < n; j++) {
                res = nums[j] * res;
                if (res >= k) {
                    count += n - j;
                    break;
                }
            }
        }
        return n * (n + 1) / 2 - count;
    }


    /**
     * <a href="https://leetcode.cn/problems/length-of-longest-subarray-with-at-most-k-frequency/description/">...</a>
     * 给你一个整数数组 nums 和一个整数 k 。
     * 一个元素 x 在数组中的 频率 指的是它在数组中的出现次数。
     * 如果一个数组中所有元素的频率都 小于等于 k ，那么我们称这个数组是 好 数组。
     * 请你返回 nums 中 最长好 子数组的长度。
     * 输入：nums = [1,2,3,1,2,3,1,2], k = 2
     * 输出：6
     * 解释：最长好子数组是 [1,2,3,1,2,3] ，值 1 ，2 和 3 在子数组中的频率都没有超过 k = 2 。[2,3,1,2,3,1] 和 [3,1,2,3,1,2] 也是好子数组。
     * 最长好子数组的长度为 6 。
     * 子数组 指的是一个数组中一段连续非空的元素序列。
     * 未完成
     */
    public int maxSubarrayLength(int[] nums, int k) {
        if (k == 0) {
            return 0;
        }
        int res = 0;
        int left = 0;
        List<Integer> integers = new ArrayList<>();
        for (int right = 0; right < nums.length; right++) {
            int finalRight = right;
            if (integers.stream().filter(num -> num == nums[finalRight]).count() >= k) {
                res = Math.max(res, right - left);
                int m = integers.indexOf(nums[right]);
                if (m >= 0) {
                    integers.subList(0, m + 1).clear();
                }
                left += m + 1;
            }
            integers.add(nums[right]);
            if (right == nums.length - 1) {
                res = Math.max(res, right - left + 1);
            }
        }

        if (res != 0) {
            return res;
        } else {
            return nums.length;
        }
    }

    /**
     * <a href="https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/">...</a>
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
     * 示例 1：
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     */
    public int[] searchRange(int[] nums, int target) {
        int start = binarySearch(nums, target);
        if (start == nums.length || nums[start] != target) {  //不存在这个target的情况
            return new int[]{-1, -1};
        }
        int end = binarySearch(nums, target + 1) - 1;
        return new int[]{start, end};
    }

    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * <a href="https://leetcode.cn/problems/maximum-count-of-positive-integer-and-negative-integer/">...</a>
     * 给你一个按 非递减顺序 排列的数组 nums ，返回正整数数目和负整数数目中的最大值。
     * 换句话讲，如果 nums 中正整数的数目是 pos ，而负整数的数目是 neg ，返回 pos 和 neg二者中的最大值。
     * 注意：0 既不是正整数也不是负整数。
     * 输入：nums = [-2,-1,-1,1,2,3]
     * 输出：3
     * 解释：共有 3 个正整数和 3 个负整数。计数得到的最大值是 3 。
     */
    public int maximumCount(int[] nums) {

        int start = binarySearch(nums, 0);
        if (start == nums.length) {
            return nums.length;
        }
        if (nums[start] != 0) {
            return Math.max(start, nums.length - start);
        }
        int end = binarySearch(nums, 1) - 1;
        return Math.max(start, nums.length - end - 1);
    }

    /**
     * <a href="https://leetcode.cn/problems/successful-pairs-of-spells-and-potions/description/">...</a>
     * 给你两个正整数数组 spells 和 potions ，长度分别为 n 和 m ，其中 spells[i] 表示第 i 个咒语的能量强度，potions[j] 表示第 j 瓶药水的能量强度。
     * 同时给你一个整数 success 。一个咒语和药水的能量强度 相乘 如果 大于等于 success ，那么它们视为一对 成功 的组合。
     * 请你返回一个长度为 n 的整数数组 pairs，其中 pairs[i] 是能跟第 i 个咒语成功组合的 药水 数目。
     * 输入：spells = [5,1,3], potions = [1,2,3,4,5], success = 7
     * 输出：[4,0,3]
     * 解释：
     * - 第 0 个咒语：5 * [1,2,3,4,5] = [5,10,15,20,25] 。总共 4 个成功组合。
     * - 第 1 个咒语：1 * [1,2,3,4,5] = [1,2,3,4,5] 。总共 0 个成功组合。
     * - 第 2 个咒语：3 * [1,2,3,4,5] = [3,6,9,12,15] 。总共 3 个成功组合。
     * 所以返回 [4,0,3] 。
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        List<Integer> integers = new ArrayList<>();
        for (int spell : spells) {
            int a = (int) Math.ceil((double) success / spell);
            int res = potions.length - binarySearch(potions, a);
            integers.add(res);
        }
        return integers.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * <a href="https://leetcode.cn/problems/count-the-number-of-fair-pairs/">...</a>
     * 给你一个下标从 0 开始、长度为 n 的整数数组 nums ，和两个整数 lower 和 upper ，返回 公平数对的数目 。
     * 如果 (i, j) 数对满足以下情况，则认为它是一个 公平数对 ：
     * 0 <= i < j < n，且
     * lower <= nums[i] + nums[j] <= upper
     * 输入：nums = [0,1,7,4,4,5], lower = 3, upper = 6
     * 输出：6
     * 解释：共计 6 个公平数对：(0,3)、(0,4)、(0,5)、(1,3)、(1,4) 和 (1,5) 。
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        int res = 0;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++) {
            int[] newNums = Arrays.copyOfRange(nums, i + 1, nums.length);
            Arrays.sort(newNums);
            int start = binarySearch(newNums, lower - nums[i]);
            int end = binarySearch(newNums, upper + 1 - nums[i]);
            res += end - start;
        }
        return res;
    }

    /**
     * <a href="https://leetcode.cn/problems/h-index-ii/">...</a>
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数，citations 已经按照 升序排列 。计算并返回该研究者的 h 指数。
     * h 指数的定义：h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （n 篇论文中）至少 有 h 篇论文分别被引用了至少 h 次。
     * 请你设计并实现对数时间复杂度的算法解决此问题。
     * 输入：citations = [0,1,3,5,6]
     * 输出：3
     * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 0, 1, 3, 5, 6 次。
     * 由于研究者有3篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
     */
    public int hIndex(int[] citations) {
        if (citations.length == 0) {
            return 0;
        }
        int left = 1;
        int right = citations.length;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[citations.length - mid] < mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * <a href="https://leetcode.cn/problems/minimum-time-to-complete-trips/description/">...</a>
     * 给你一个数组 time ，其中 time[i] 表示第 i 辆公交车完成 一趟旅途 所需要花费的时间。
     * 每辆公交车可以 连续 完成多趟旅途，也就是说，一辆公交车当前旅途完成后，可以 立马开始 下一趟旅途。每辆公交车 独立 运行，也就是说可以同时有多辆公交车在运行且互不影响。
     * 给你一个整数 totalTrips ，表示所有公交车 总共 需要完成的旅途数目。请你返回完成 至少 totalTrips 趟旅途需要花费的 最少 时间。
     * 输入：time = [1,2,3], totalTrips = 5
     * 输出：3
     * 解释：
     * - 时刻 t = 1 ，每辆公交车完成的旅途数分别为 [1,0,0] 。
     * 已完成的总旅途数为 1 + 0 + 0 = 1 。
     * - 时刻 t = 2 ，每辆公交车完成的旅途数分别为 [2,1,0] 。
     * 已完成的总旅途数为 2 + 1 + 0 = 3 。
     * - 时刻 t = 3 ，每辆公交车完成的旅途数分别为 [3,1,1] 。
     * 已完成的总旅途数为 3 + 1 + 1 = 5 。
     * 所以总共完成至少 5 趟旅途的最少时间为 3 。
     */
    public long minimumTime(int[] time, int totalTrips) {
        long minT = Long.MAX_VALUE;
        for (int j : time
        ) {
            minT = Math.min(j, minT);
        }
        long left = minT - 1;
        long right = minT * totalTrips;
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            if (check(time, totalTrips, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int[] time, int totalTrips, long h) {
        long sum = 0;
        for (int j : time) {
            sum += h / j;
            if (sum >= totalTrips) {
                return true;
            }
        }
        return false;
    }

    /**
     * <a href="https://leetcode.cn/problems/minimize-maximum-of-array/description/">...</a>
     * 最小化数组中的最大值
     * 峰值元素是指其值严格大于左右相邻值的元素。
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
     * 输入：nums = [1,2,3,1]
     * 输出：2
     * 解释：3 是峰值元素，你的函数应该返回其索引 2。
     */
    public int findPeakElement(int[] nums) {
//        int left = -1;
//        int right = nums.length - 1;//(-1,n-1)区间
//        while (left + 1 < right) {
//            int mid = (left + right) >>> 1;
//            if (nums[mid] > nums[mid + 1]) {
//                right = mid;
//            } else {
//                left = mid;
//            }
//        }
//        return right;
        int left = 0;
        int right = nums.length - 2;//[0,n-2]闭区间
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > nums[mid + 1]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 2;//[0,n-2]
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > nums[nums.length - 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return nums[left];
    }

    /**
     * <a href="https://leetcode.cn/problems/search-in-rotated-sorted-array/description/">...</a>
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     */
    public int search(int[] nums, int target) {
        if (nums.length == 1) {
            if (nums[0] == target) {
                return 0;
            } else {
                return -1;
            }
        }
        int left = 0;
        int right = nums.length - 2;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > nums[nums.length - 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int start, end;
        if (nums[0] > target) {
            start = left;
            end = nums.length - 1;
        } else {
            start = 0;
            end = left - 1;
        }
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * <a href="https://leetcode.cn/problems/find-a-peak-element-ii/description/">...</a>
     * 一个 2D 网格中的 峰值 是指那些 严格大于 其相邻格子(上、下、左、右)的元素
     * 给你一个 从 0 开始编号 的 m x n 矩阵 mat ，其中任意两个相邻格子的值都 不相同 。找出 任意一个 峰值 mat[i][j] 并 返回其位置 [i,j] 。
     * 你可以假设整个矩阵周边环绕着一圈值为 -1 的格子。
     * 要求必须写出时间复杂度为 O(m log(n)) 或 O(n log(m)) 的算法
     * 输入: mat = [[1,4],[3,2]]
     * 输出: [0,1]
     * 解释: 3 和 4 都是峰值，所以[1,0]和[0,1]都是可接受的答案。
     */
    public int[] findPeakGrid(int[][] mat) {
        if (mat.length == 1 && mat[0].length == 1) {
            return new int[]{0, 0};
        }

    }
}