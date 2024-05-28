const A="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIcAAAAlCAYAAABoM/rvAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkNFQ0RDQzMxNDg5RjExRTZBNUY0OTQ3NTQzNEFDMUNDIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkNFQ0RDQzMyNDg5RjExRTZBNUY0OTQ3NTQzNEFDMUNDIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6Q0VDRENDMkY0ODlGMTFFNkE1RjQ5NDc1NDM0QUMxQ0MiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6Q0VDRENDMzA0ODlGMTFFNkE1RjQ5NDc1NDM0QUMxQ0MiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6FNWhTAAAJFklEQVR42uxc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";export{A as w};