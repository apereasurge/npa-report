import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INpaReport, defaultValue } from 'app/shared/model/npa-report.model';

export const ACTION_TYPES = {
  FETCH_NPAREPORT_LIST: 'npaReport/FETCH_NPAREPORT_LIST',
  FETCH_NPAREPORT: 'npaReport/FETCH_NPAREPORT',
  CREATE_NPAREPORT: 'npaReport/CREATE_NPAREPORT',
  UPDATE_NPAREPORT: 'npaReport/UPDATE_NPAREPORT',
  DELETE_NPAREPORT: 'npaReport/DELETE_NPAREPORT',
  RESET: 'npaReport/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INpaReport>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type NpaReportState = Readonly<typeof initialState>;

// Reducer

export default (state: NpaReportState = initialState, action): NpaReportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NPAREPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NPAREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_NPAREPORT):
    case REQUEST(ACTION_TYPES.UPDATE_NPAREPORT):
    case REQUEST(ACTION_TYPES.DELETE_NPAREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_NPAREPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NPAREPORT):
    case FAILURE(ACTION_TYPES.CREATE_NPAREPORT):
    case FAILURE(ACTION_TYPES.UPDATE_NPAREPORT):
    case FAILURE(ACTION_TYPES.DELETE_NPAREPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NPAREPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NPAREPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_NPAREPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_NPAREPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_NPAREPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/npa-reports';

// Actions

export const getEntities: ICrudGetAllAction<INpaReport> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NPAREPORT_LIST,
  payload: axios.get<INpaReport>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<INpaReport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NPAREPORT,
    payload: axios.get<INpaReport>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<INpaReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NPAREPORT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INpaReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NPAREPORT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<INpaReport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NPAREPORT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
