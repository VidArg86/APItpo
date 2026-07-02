import { useEffect, useState } from 'react';
import { fetchImageBlobUrl } from '../../services/api';

const ProductImage = ({ productoId, imagenId, alt, className }) => {
  const [url, setUrl] = useState(null);

  useEffect(() => {
    let objectUrl;
    let cancelled = false;

    if (imagenId) {
      fetchImageBlobUrl(`/${productoId}/imagenes/${imagenId}`)
        .then((blobUrl) => {
          if (cancelled) return;
          objectUrl = blobUrl;
          setUrl(blobUrl);
        })
        .catch(() => setUrl(null));
    }

    return () => {
      cancelled = true;
      if (objectUrl) URL.revokeObjectURL(objectUrl);
    };
  }, [productoId, imagenId]);

  if (!url) {
    return <div className={`${className} admin-img-placeholder`} aria-hidden="true">🎂</div>;
  }

  return <img src={url} alt={alt} className={className} />;
};

export default ProductImage;
