package eu.ladeira.grafana;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.Dashboard;
import com.grafana.foundation.dashboard.DashboardBuilder;
import com.grafana.foundation.dashboard.DashboardDashboardTimeBuilder;
import com.grafana.foundation.dashboard.RowBuilder;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.timeseries.PanelBuilder;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Dashboard dashboard = new DashboardBuilder("Instance1 - System Metrics").
                uid("instance1-system-metrics")
                .refresh("1m")
                .time(new DashboardDashboardTimeBuilder()
                        .from("now-30m")
                        .to("now")
                )
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder("Overview"))
                .withPanel(new PanelBuilder()
                        .title("Instance1 - CPU Usage (avg, %)")
                        .unit("%")
                        .min(0.0)
                        .withTarget(new DataqueryBuilder()
                                .expr("cpu_usage")
                        )
                ).withPanel(new PanelBuilder()
                        .title("Instance1 - CPU Usage (p95, %)")
                        .unit("%")
                        .min(0.0)
                        .withTarget(new DataqueryBuilder()
                                .expr("quantile_over_time(0.95, cpu_usage[5m])")
                        )
                ).build();

        System.out.println(dashboard.toJSON());
    }
}
