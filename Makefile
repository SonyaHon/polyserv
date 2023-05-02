ifndef DEV_MONGO_PORT
override DEV_MONGO_PORT=27017
endif

ifndef DEV_REPL_PORT
override DEV_REPL_PORT=16969
endif


ifndef DEV_APP_PORT
override DEV_APP_PORT=6969
endif


ifndef DEV_REDIS_PORT
override DEV_REDIS_PORT=6379
endif

DOCKER_CLI = DEV_APP_PORT=$(DEV_APP_PORT) DEV_REPL_PORT=$(DEV_REPL_PORT) DEV_REDIS_PORT=$(DEV_REDIS_PORT) DEV_MONGO_PORT=$(DEV_MONGO_PORT) DOCKER_BUILDKIT=1 docker compose -f ./docker-compose.yml -p polyserv
ifdef BUILD
override POSTFIX = --build
endif

ifndef TARGET
override TARGET=polyserv
endif

all:
	@echo "Polyserv:"
	@echo ""
	@echo " Options:"
	@echo "    Add BUILD=1 to run build"
	@echo "    Use TARGET=<name> to run command against a particular service"
	@echo ""
	@echo " Commands:"
	@echo "    make start - start all services in dev environment"
	@echo "    make stop  - stop all services in dev environment"
	@echo "    make logs  - show logs for service (default - polyserv)"

start:
	$(DOCKER_CLI) up -d $(POSTFIX)


stop:
	$(DOCKER_CLI) down --remove-orphans

logs:
	$(DOCKER_CLI) logs -f $(TARGET)

poly:
	$(DOCKER_CLI) exec polyserv clj -M:poly

update-deps:
	$(DOCKER_CLI) exec polyserv clj -X:deps prep :aliases '[:nrepl :dev :poly :test]'