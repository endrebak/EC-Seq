(ns test-routes
  (:use midje.sweet)
  (:require  [rna.routes.home :as h]))

(def query-result [{:p_value 0.0331861, :ovsy_p_value 2.90255E-5, :mecvslec_logfc 0.298186, :eciivsd_p_value 0.00295655, :mecvslec_adj_p_val 0.428281, :mecvslec_aveexpr 0.212898, :eciivsd_aveexpr 0.212898, :mecvslec_t 1.43435, :ovsy_adj_p_val 5.70682E-5, :mecvslec_b -5.11103, :id "Shh", :mecvslec_p_value 0.157672, :ovsy_aveexpr 0.212898, :b -3.97282, :ovsy_t -4.5989, :t -2.19013, :eciivsd_b -2.21109, :eciivsd_adj_p_val 0.00898955, :logfc -0.628142, :ovsy_logfc -0.956067, :ovsy_b 1.6452, :eciivsd_t -3.12453, :eciivsd_logfc -0.649558, :adj_p_val 0.0897154}])

(def mygene-in-local-db [{:name "sonic hedgehog", :symbol "Shh"} {:name "GLI family zinc finger 3", :symbol "Gli3"} {:name "ADCYAP receptor type 1", :symbol "Adcyap1r1"}])

(def toptable-results [{:p_value 8.02769E-5, :ovsy_p_value 5.28397E-22, :mecvslec_logfc -0.185605, :eciivsd_p_value 1.52756E-5, :mecvslec_adj_p_val 0.00918781, :mecvslec_aveexpr 11.3112, :eciivsd_aveexpr 11.3112, :mecvslec_t -3.61234, :ovsy_adj_p_val 4.78072E-21, :mecvslec_b -1.43422, :id "Zwint", :mecvslec_p_value 7.0078E-4, :ovsy_aveexpr 11.3112, :b 0.546756, :ovsy_t -16.5744, :t -4.29369, :eciivsd_b 1.67929, :eciivsd_adj_p_val 8.67177E-5, :logfc -0.311997, :ovsy_logfc -0.851604, :ovsy_b 39.2024, :eciivsd_t -4.788, :eciivsd_logfc -0.246011, :adj_p_val 6.10449E-4} {:p_value 0.261977, :ovsy_p_value 0.350712, :mecvslec_logfc 0.235577, :eciivsd_p_value 0.0433745, :mecvslec_adj_p_val 0.0133941, :mecvslec_aveexpr 5.19365, :eciivsd_aveexpr 5.19365, :mecvslec_t 3.45587, :ovsy_adj_p_val 0.420018, :mecvslec_b -1.71747, :id "Zyx", :mecvslec_p_value 0.00112477, :ovsy_aveexpr 5.19365, :b -6.64065, :ovsy_t 0.941982, :t 1.13449, :eciivsd_b -5.66063, :eciivsd_adj_p_val 0.0904392, :logfc 0.105676, :ovsy_logfc 0.0642123, :ovsy_b -7.93893, :eciivsd_t 2.07247, :eciivsd_logfc 0.141275, :adj_p_val 0.429254} {:p_value 0.165135, :ovsy_p_value 2.91161E-14, :mecvslec_logfc 0.0119934, :eciivsd_p_value 0.158953, :mecvslec_adj_p_val 0.899378, :mecvslec_aveexpr 6.11175, :eciivsd_aveexpr 6.11175, :mecvslec_t 0.333395, :ovsy_adj_p_val 1.22389E-13, :mecvslec_b -7.18503, :id "Zzz3", :mecvslec_p_value 0.740224, :ovsy_aveexpr 6.11175, :b -6.37047, :ovsy_t 10.4965, :t 1.40853, :eciivsd_b -6.83566, :eciivsd_adj_p_val 0.262847, :logfc 0.070956, :ovsy_logfc 0.377593, :ovsy_b 21.2299, :eciivsd_t 1.42985, :eciivsd_logfc 0.0514366, :adj_p_val 0.307311}])

(fact "About toptable-row-relevant-keys"
      (h/toptable-row-relevant-keys "eciivsd" (first toptable-results)) => [:eciivsd_adj_p_val :eciivsd_aveexpr :eciivsd_b :eciivsd_logfc :eciivsd_p_value :eciivsd_t :id])

(fact "About toptable-relevant-items"
      (h/toptable-relevant-items "eciivsd" toptable-results) => [{:id "Zwint" :eciivsd_adj_p_val 8.67177E-5, :eciivsd_aveexpr 11.3112, :eciivsd_b 1.67929, :eciivsd_logfc -0.246011, :eciivsd_p_value 1.52756E-5, :eciivsd_t -4.788} {:id "Zyx" :eciivsd_adj_p_val 0.0904392, :eciivsd_aveexpr 5.19365, :eciivsd_b -5.66063, :eciivsd_logfc 0.141275, :eciivsd_p_value 0.0433745, :eciivsd_t 2.07247} {:id "Zzz3" :eciivsd_adj_p_val 0.262847, :eciivsd_aveexpr 6.11175, :eciivsd_b -6.83566, :eciivsd_logfc 0.0514366, :eciivsd_p_value 0.158953, :eciivsd_t 1.42985}])

(def tt-relevant-items-result [{:id "Zwint" :eciivsd_adj_p_val 8.67177E-5, :eciivsd_aveexpr 11.3112, :eciivsd_b 1.67929, :eciivsd_logfc -0.246011, :eciivsd_p_value 1.52756E-5, :eciivsd_t -4.788} {:id "Zyx" :eciivsd_adj_p_val 0.0904392, :eciivsd_aveexpr 5.19365, :eciivsd_b -5.66063, :eciivsd_logfc 0.141275, :eciivsd_p_value 0.0433745, :eciivsd_t 2.07247} {:id "Zzz3" :eciivsd_adj_p_val 0.262847, :eciivsd_aveexpr 6.11175, :eciivsd_b -6.83566, :eciivsd_logfc 0.0514366, :eciivsd_p_value 0.158953, :eciivsd_t 1.42985}])

(fact "About toptable-rename-items"
      (h/toptable-rename-items "eciivsd" tt-relevant-items-result) => [{:adj_p_val 8.67177E-5, :aveexpr 11.3112, :b 1.67929, :id "Zwint", :logfc -0.246011, :p_value 1.52756E-5, :t -4.788} {:adj_p_val 0.0904392, :aveexpr 5.19365, :b -5.66063, :id "Zyx", :logfc 0.141275, :p_value 0.0433745, :t 2.07247} {:adj_p_val 0.262847, :aveexpr 6.11175, :b -6.83566, :id "Zzz3", :logfc 0.0514366, :p_value 0.158953, :t 1.42985}])


(fact "About parse-toptable"
      (h/parse-toptable "eciivsd" toptable-results) => [{:adj_p_val 8.67177E-5, :aveexpr 11.3112, :b 1.67929, :id "Zwint", :logfc -0.246011, :p_value 1.52756E-5, :t -4.788} {:adj_p_val 0.0904392, :aveexpr 5.19365, :b -5.66063, :id "Zyx", :logfc 0.141275, :p_value 0.0433745, :t 2.07247} {:adj_p_val 0.262847, :aveexpr 6.11175, :b -6.83566, :id "Zzz3", :logfc 0.0514366, :p_value 0.158953, :t 1.42985}])
