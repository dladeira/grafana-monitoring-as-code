package eu.ladeira.grafana;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grafana.foundation.common.BigValueGraphMode;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.*;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.timeseries.PanelBuilder;

public class Main {
    private static final String DASHBOARD_TITLE = "Instance1 - System Metrics";
    private static final String DASHBOARD_UID = "instance1-system-metrics";
    private static final String DASHBOARD_REFRESH = "1m";
    private static final String TIME_FROM = "now-30m";
    private static final String TIME_TO = "now";

    private static final String ROW_TITLE_CURRENT = "Current CPU Usage";
    private static final String ROW_TITLE_USAGE = "Usage Over time";

    private static final String TITLE_CPU_CURRENT = "Current CPU Usage (%)";
    private static final String EXPR_CPU_CURRENT = "cpu_usage";

    private static final String TITLE_CPU_P95_30M = "CPU Usage - P95 (Last 30min, %)";
    private static final String EXPR_CPU_P95_30M = "quantile_over_time(0.95, cpu_usage[30m])";

    private static final String TITLE_CPU_AVG = "Instance1 - CPU Usage (avg 1min, %)";
    private static final String EXPR_CPU_AVG = "avg_over_time(cpu_usage[1m])";

    private static final String TITLE_CPU_P95_5M = "Instance1 - CPU Usage (p95 5min, %)";
    private static final String EXPR_CPU_P95_5M = "quantile_over_time(0.95, cpu_usage[5m])";

    public static void main(String[] args) {
        try {
            Dashboard dashboard = buildDashboard();
            System.out.println(dashboard.toJSON());
        } catch (JsonProcessingException e) {
            System.err.println("Failed to serialize dashboard to JSON: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static Dashboard buildDashboard() {
        return new DashboardBuilder(DASHBOARD_TITLE)
                .uid(DASHBOARD_UID)
                .refresh(DASHBOARD_REFRESH)
                .time(new DashboardDashboardTimeBuilder()
                        .from(TIME_FROM)
                        .to(TIME_TO)
                )
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder(ROW_TITLE_CURRENT))
                .withPanel(buildCurrentCpuStatPanel())
                .withPanel(buildCpuP95Last30MinStatPanel())
                .withRow(new RowBuilder(ROW_TITLE_USAGE))
                .withPanel(buildCpuAvgTimeseriesPanel())
                .withPanel(buildCpuP95Last5MinTimeseriesPanel())
                .build();
    }

    private static com.grafana.foundation.stat.PanelBuilder buildCurrentCpuStatPanel() {
        return new com.grafana.foundation.stat.PanelBuilder()
                .title(TITLE_CPU_CURRENT)
                .unit("percent")
                .graphMode(BigValueGraphMode.NONE)
                .transparent(true)
                .withTarget(new DataqueryBuilder().expr(EXPR_CPU_CURRENT));
    }

    private static com.grafana.foundation.stat.PanelBuilder buildCpuP95Last30MinStatPanel() {
        return new com.grafana.foundation.stat.PanelBuilder()
                .title(TITLE_CPU_P95_30M)
                .unit("percent")
                .graphMode(BigValueGraphMode.NONE)
                .transparent(true)
                .withTarget(new DataqueryBuilder().expr(EXPR_CPU_P95_30M));
    }

    private static PanelBuilder buildCpuAvgTimeseriesPanel() {
        return new PanelBuilder()
                .title(TITLE_CPU_AVG)
                .unit("percent")
                .min(0.0)
                .withTarget(new DataqueryBuilder().expr(EXPR_CPU_AVG));
    }

    private static PanelBuilder buildCpuP95Last5MinTimeseriesPanel() {
        return new PanelBuilder()
                .title(TITLE_CPU_P95_5M)
                .unit("percent")
                .min(0.0)
                .withTarget(new DataqueryBuilder().expr(EXPR_CPU_P95_5M));
    }
}
