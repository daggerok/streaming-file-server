#!/usr/bin/env bash

# debug
#set -x
#LOG_LEVEL=debug

# app info
VERSION=${project.version}
COMPOSE_FILE="docker-compose.yml"
FILE_SERVER_FILENAME="file-server-${VERSION}.jar"
FILE_ITEMS_SERVICE_FILENAME="file-items-service-${VERSION}.jar"
RELEASE_BASE_PATH="https://daggerok.github.io/streaming-file-server/app"
APPLICATION_PATH="app"

if [ "'$LOG_LEVEL'" == "'debug'" ]; then
  echo "version                   : $VERSION"
  echo "application path          : $APPLICATION_PATH"
  echo "docker-compose file       : $COMPOSE_FILE"
  echo "file-server               : $FILE_SERVER_FILENAME"
  echo "file-items-service        : $FILE_ITEMS_SERVICE_FILENAME"
  echo
fi

# required binaries info

# args
ARGS=$#
SCRIPT="$0"
APPLICATION_COMMAND="$1"
FILE_STORAGE_PATH="$2"

if [ "'$LOG_LEVEL'" == "'debug'" ]; then
  echo "ARGS                      : '$ARGS'"
  echo "SCRIPT                    : '$SCRIPT'"
  echo "FILE_STORAGE_PATH         : '$FILE_STORAGE_PATH'"
  echo "APPLICATION_COMMAND       : '$APPLICATION_COMMAND'"
  echo
fi

function FAIL_STOP_WITH_USAGE_FUNC {
  echo "Usage:"
  echo ""
  echo "start                     : $0 start \$PATH_TO_FILE_STORAGE"
  echo "stop                      : $0 stop"
  echo "cleanup and stop          : $0 clean \$PATH_TO_FILE_STORAGE"
  echo
  exit 1
}

function VALIDATE_INPUTS_FUNC {
  if ( [ "'$APPLICATION_COMMAND'" != "'start'" ] \
    && [ "'$APPLICATION_COMMAND'" != "'stop'" ] \
    && [ "'$APPLICATION_COMMAND'" != "'clean'" ] )
    then
      FAIL_STOP_WITH_USAGE_FUNC
  fi

  if ([ "'$APPLICATION_COMMAND'" == "'start'" ] || [ "'$APPLICATION_COMMAND'" == "'clean'" ]) && [ ${ARGS} -ne 2 ]; then
    FAIL_STOP_WITH_USAGE_FUNC
  fi
}

VALIDATE_INPUTS_FUNC

# docker compose
function GET_COMPOSE_FUNC {
  if [ ! -f "${APPLICATION_PATH}/${COMPOSE_FILE}" ]; then
    mkdir -p ${APPLICATION_PATH}
    wget -P ${APPLICATION_PATH} "${RELEASE_BASE_PATH}/$COMPOSE_FILE"
  fi
}

function START_DATABASE_FUNC {
  GET_COMPOSE_FUNC
  docker-compose -f "${APPLICATION_PATH}/${COMPOSE_FILE}" up -d
}

# application
function GET_APPLICATION_FUNC {
  if [ ! -f "${APPLICATION_PATH}/${FILE_SERVER_FILENAME}" ]; then
    mkdir -p ${APPLICATION_PATH}
    wget -P ${APPLICATION_PATH} "${RELEASE_BASE_PATH}/$FILE_SERVER_FILENAME"
    wget -P ${APPLICATION_PATH} "${RELEASE_BASE_PATH}/$FILE_ITEMS_SERVICE_FILENAME"
  fi
}

function WAIT_FOR {
  if [ "'$1'" != "'health'" ]; then
    return
  fi
  HEALTH_CHECK_URL="http://localhost:"$2"/actuator/health"
  (exit -1)
  while (($?))
  do
    sleep 1
    wget -P ${APPLICATION_PATH} -q --spider ${HEALTH_CHECK_URL}
  done
}

function START_APPLICATION_FUNC {
  GET_COMPOSE_FUNC
  START_DATABASE_FUNC
  GET_APPLICATION_FUNC

  bash "${APPLICATION_PATH}/${FILE_ITEMS_SERVICE_FILENAME}" --spring.profiles.active=db-pg &
  WAIT_FOR "health" 8001

  bash "${APPLICATION_PATH}/${FILE_SERVER_FILENAME}" --app.upload.path="$FILE_STORAGE_PATH" &
  WAIT_FOR "health" 8002
}

if [ "'$APPLICATION_COMMAND'" == "'start'" ]; then
  START_APPLICATION_FUNC
fi

function STOP_APPLICATION_FUNC {
  for F_NAME in ${FILE_SERVER_FILENAME} ${FILE_ITEMS_SERVICE_FILENAME}; do
    APPLICATION_PID=$(jps|grep ${F_NAME}|grep -v 'grep'|awk '{print $1}')
    for P_ID in "$APPLICATION_PID"; do
      if [ "''" != "'$P_ID'" ]; then
        echo "kill -9 $P_ID";
        kill "-9" ${P_ID}
      fi
    done
  done
}

function STOP_DATABASE_FUNC {
  if [ "'$1'" != "'--download=false'" ]; then
    GET_COMPOSE_FUNC
  fi
  docker-compose -f "${APPLICATION_PATH}/${COMPOSE_FILE}" down -v
}

if [ "'$APPLICATION_COMMAND'" == "'stop'" ]; then
  STOP_APPLICATION_FUNC
  STOP_DATABASE_FUNC
  exit 0;
fi

function CLEANUP_FUNC {
  STOP_APPLICATION_FUNC
  STOP_DATABASE_FUNC --download=false

  if [ "'$FILE_STORAGE_PATH'" != "''" ]; then
    read -p "Are you sure about removing '$FILE_STORAGE_PATH'? " -n 1 -r
    echo # move to a new line

    if [[ $REPLY =~ ^[Yy]$ ]]; then
      rm -rf ${FILE_STORAGE_PATH}
    fi
  fi

  if [ -f "${APPLICATION_PATH}" ]; then
    rm -rf "${APPLICATION_PATH}"
  fi
}

if [ "'$APPLICATION_COMMAND'" == "'clean'" ]; then
  CLEANUP_FUNC
  exit 0
fi
