import router, { errorRoutes } from "../router";

export const generateRoutes = (routes) => {
  if (routes.length > 0) router.addRoutes(routes)
  router.addRoutes(errorRoutes)
}
