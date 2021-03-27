import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NpaReport from './npa-report';
import NpaReportDetail from './npa-report-detail';
import NpaReportUpdate from './npa-report-update';
import NpaReportDeleteDialog from './npa-report-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NpaReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NpaReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NpaReportDetail} />
      <ErrorBoundaryRoute path={match.url} component={NpaReport} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NpaReportDeleteDialog} />
  </>
);

export default Routes;
