.PHONY: build json clean

build:
	docker build -t grafana-monitoring-as-code .

json: build
	docker run --rm grafana-monitoring-as-code > latest-dashboard.json
	@echo "Wrote latest-dashboard.json"

clean:
	rm -f latest-dashboard.json

