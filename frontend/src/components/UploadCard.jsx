import { useRef, useState } from 'react';
import { UploadCloud, Image as ImageIcon, Loader2, X } from 'lucide-react';

function UploadCard({ onUpload, isLoading, progress }) {
  const [dragging, setDragging] = useState(false);
  const [selectedName, setSelectedName] = useState('');
  const inputRef = useRef(null);

  const processFile = (file) => {
    if (!file) return;
    setSelectedName(file.name);
    onUpload(file);
  };

  const onDrop = (e) => {
    e.preventDefault();
    setDragging(false);
    processFile(e.dataTransfer.files?.[0]);
  };

  const clearFile = (e) => {
    e.stopPropagation();
    setSelectedName('');
    if (inputRef.current) inputRef.current.value = '';
  };

  return (
    <div className="glass-card p-6">
      <div className="mb-5">
        <h2 className="text-base font-semibold text-white">Upload an Image</h2>
        <p className="mt-0.5 text-xs text-brand-muted">JPG, PNG, WEBP or GIF &middot; up to 10 MB</p>
      </div>

      <div
        role="button"
        tabIndex={0}
        onDragOver={(e) => { e.preventDefault(); setDragging(true); }}
        onDragLeave={() => setDragging(false)}
        onDrop={onDrop}
        onClick={() => !isLoading && inputRef.current?.click()}
        onKeyDown={(e) => e.key === 'Enter' && !isLoading && inputRef.current?.click()}
        className={[
          'relative cursor-pointer overflow-hidden rounded-2xl border-2 border-dashed p-10 text-center transition-all duration-200',
          dragging
            ? 'scale-[1.01] border-brand-accent bg-brand-accent/5'
            : 'border-white/10 hover:border-brand-accent/40 hover:bg-white/[0.02]',
          isLoading ? 'pointer-events-none opacity-60' : ''
        ].filter(Boolean).join(' ')}
      >
        <div className="flex flex-col items-center gap-3">
          <div
            className={[
              'flex h-14 w-14 items-center justify-center rounded-2xl transition-colors duration-200',
              dragging ? 'bg-brand-accent/15' : 'bg-white/5'
            ].join(' ')}
          >
            <UploadCloud className={`h-7 w-7 transition-colors duration-200 ${dragging ? 'text-brand-accent' : 'text-brand-muted'}`} />
          </div>
          <div>
            <p className="text-sm font-medium text-brand-text">
              {dragging ? 'Release to analyze' : 'Drag & drop your image here'}
            </p>
            <p className="mt-1 text-xs text-brand-muted">
              or{' '}
              <span className="text-brand-accent underline-offset-2 hover:underline">browse files</span>
            </p>
          </div>
          {selectedName && (
            <div className="mt-1 inline-flex items-center gap-2 rounded-full border border-white/10 bg-brand-panelSoft/80 px-3 py-1 text-xs text-brand-text">
              <ImageIcon className="h-3.5 w-3.5 shrink-0 text-brand-accent" />
              <span className="max-w-[180px] truncate">{selectedName}</span>
              <button onClick={clearFile} className="ml-0.5 text-brand-muted hover:text-brand-text">
                <X className="h-3 w-3" />
              </button>
            </div>
          )}
        </div>
      </div>

      <input
        ref={inputRef}
        type="file"
        className="hidden"
        accept="image/*"
        onChange={(e) => processFile(e.target.files?.[0])}
      />

      {isLoading && (
        <div className="mt-5 space-y-3">
          <div className="flex items-center gap-2.5 text-brand-muted">
            <Loader2 className="h-4 w-4 animate-spin text-brand-accent" />
            <span className="text-xs">Uploading & running analysis...</span>
          </div>
          <div className="h-1.5 w-full overflow-hidden rounded-full bg-white/8">
            <div
              className="h-full rounded-full bg-gradient-to-r from-brand-accent to-brand-primary transition-all duration-300"
              style={{ width: `${progress}%` }}
            />
          </div>
          <p className="text-right text-[11px] tabular-nums text-brand-muted">{progress}%</p>
        </div>
      )}
    </div>
  );
}

export default UploadCard;
